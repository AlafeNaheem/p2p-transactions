/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milestone.ict.p2ptransactions.repository;

import com.milestone.ict.p2ptransactions.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Babajide
 */
public interface UserAccountRepo extends CrudRepository<UserAccount, Integer> {
    
}
