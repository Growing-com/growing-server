package org.sarangchurch.growing.training.presentation.discipleship;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.domain.discipleship.DiscipleshipRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteDiscipleshipController {
    private final DiscipleshipRepository discipleshipRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/discipleship/{discipleshipId}")
    public void deleteDiscipleship(@PathVariable Long discipleshipId) {
        try {
            discipleshipRepository.deleteById(discipleshipId);
        } catch (EmptyResultDataAccessException e) {
            // Ignore
        } catch (Exception e) {
            throw e;
        }
    }
}
