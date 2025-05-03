package com.kramekk1.medicalClinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VisitValidator {

    public static void validateVisitCreation(CreateVisitCommand command, VisitRepository visitRepository) {
        validateNotInPast(command);
        validateQuarterHourStart(command);
        validateVisitsNotInTheSameTime(command, visitRepository);
        validateEndTimeIsBeforeStartTime(command);
    }

    public static void validateVisitBooking(Visit visit) {
        validateVisitNotAlreadyBooked(visit);
        validateNotInPast(visit);
    }

    public static void validateNotInPast(CreateVisitCommand command) {
        if (command.getStartAt().isBefore(LocalDateTime.now())) {
            throw new VisitInThePastException("You cannot create visit in past", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateNotInPast(Visit visit) {
        if (visit.getStartAt().isBefore(LocalDateTime.now())) {
            throw new VisitInThePastException("You cannot book visit from past", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateQuarterHourStart(CreateVisitCommand command) {
        if (command.getStartAt().getMinute() % 15 != 0 || command.getEndTime().getMinute() % 15 != 0) {
            throw new VisitNotInQuarterHourException("The visit must start at least every quarter of hour", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateVisitsNotInTheSameTime(CreateVisitCommand command, VisitRepository visitRepository) {
        Long doctorId = command.getDoctorId();
        LocalDateTime commandStartAt = command.getStartAt();
        LocalDateTime commandEndTime = command.getEndTime();

        boolean overlaps = visitRepository.findAll()
                .stream()
                .filter(visit -> doctorId.equals(visit.getDoctor().getId()))
                .anyMatch(visit ->
                        commandStartAt.isBefore(visit.getEndTime()) &&
                                visit.getStartAt().isBefore(commandEndTime)
                );

        if (overlaps) {
            throw new VisitsInTheSameTimeException("Visit which you creating, clashes with another one", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateVisitNotAlreadyBooked(Visit visit) {
        if (visit.getPatient() != null) {
            throw new VisitAlreadyBookedException("Visit which you choose is already booked", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateEndTimeIsBeforeStartTime(CreateVisitCommand command) {
        if (command.getEndTime().isBefore(command.getStartAt())) {
            throw new VisitTimeRangeException("Visit end time is before input start time", HttpStatus.BAD_REQUEST);
        }
    }
}
