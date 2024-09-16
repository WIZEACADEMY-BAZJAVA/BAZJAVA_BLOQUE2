package com.wizeline.maven.LearningJava.service;

import com.wizeline.maven.LearningJava.model.BankAccountDTO;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    /**
     * Gets accounts.
     *
     * @return todas las cuentas existentes en formato de lista.
     */
    List<BankAccountDTO> getAccounts();

    /**
     * Gets account details.
     *
     * @param user      nombre de usuario.
     * @param lastUsage último uso de la cuenta.
     * @return detalles de una sola cuenta.
     */
    BankAccountDTO getAccountDetails(String user, String lastUsage);

    void deleteAccounts();

    /**
     * Get Accounts user
     *
     * @param user nombre de usuario
     * @return Todas las cuentas del usuario, si no se manda un usuario se obtienen las de uno por default
     */


    List<BankAccountDTO> getAccountByUser(Optional<String> user);

    /**
     * Update Accounts user
     *
     * @param oldUser nombre del usuario a actualizar.
     * @param newUser nuevo nombre del usuario al que pasaran las cuentas.
     * @return todas las cuentas para verificar que las cuentas se actualizaron
     */

    List<BankAccountDTO> updateUserAccounts(String oldUser, String newUser);


}
