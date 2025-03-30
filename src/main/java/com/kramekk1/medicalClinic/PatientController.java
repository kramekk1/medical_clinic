package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Adnotacja mowiaca ze klasa jest beanem typu controller- obslugujaca endpointy na bazie protokołu http
@RequiredArgsConstructor //adnotacja tworzaca konstruktor w momencie kompilacji na podstawie pól finalnych
@RequestMapping("/patients") //określenie ścieżki pod która będą obsługiwane zapytania
public class PatientController {

    private final PatientService patientService; // pole finalne, które z @ReqArgsConst tworzy konstruktor z tym polem i dzieki temu wstrzykuje zaleznosci klasy service, która jest tutaj beanem

    @GetMapping // pobierz zasób z serwera
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}") // pobierz zasób z serwera na podstawie adresu email przekazanego w ścieżce -> @PathVariable
    public Optional<Patient> getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    @ResponseStatus(HttpStatus.CREATED) // zwrocenie statusu http 201 CREATED
    @PostMapping() // utworz zasob na serwerze pod sciezka
    public Patient addPatient(@RequestBody Patient patient) { // @RequestBody pobierze przekazane przez zapytanie body i na jego podstawie utworzy obiekt Patient
        patientService.addPatient(patient);
        return patient;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    // zwrocenie statusu 204 NO CONTENT gdy zamowienie sie przetworzy i nie jest przewidziana zadna tresc odpowiedz w body
    @PutMapping("/{email}") // calosciowa edycja istniejacego zasobu
    public void updatePatient(@PathVariable String email, @RequestBody Patient newPatient) {
        patientService.editPatientByEmail(email, newPatient);
    }

    @DeleteMapping("/{email}") // usuwanie zasobu na podstawie sciezki /{email}
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{email}")
    public void editPasswordByEmail(@PathVariable String email, @RequestBody PatientPassword newPassword) {
        patientService.editPasswordByEmail(email, newPassword.getPassword());
    }
}
