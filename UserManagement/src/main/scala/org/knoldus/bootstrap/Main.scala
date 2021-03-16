// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.knoldus.bootstrap

import org.knoldus.db.{DataAccessObject, UserRepository}
import org.knoldus.model.{User, UserType}
import org.knoldus.request.UserService
import org.knoldus.validator.Validator

object Main extends App{

    val customer = User(None,"aditya111", "aditya111@gmail.com", Option("Noida"), "7988313043",UserType.Customer)
    val admin = User(None, "rahul", "rahul1996@gmail.com", Option("Gurgaon"), "8724835905", UserType.Admin)
    val updatedAdmin =User(None, "rahul1996", "rahul1996@gmail.com", Option("Gurgaon"), "8724835905", UserType.Admin)

    val userRepository : DataAccessObject[User] = new UserRepository
    val userService = new UserService(userRepository,new Validator)


    print("Creating Customer and Admin User...\n")
    val customerId = userService.addUser(customer)
    val adminId = userService.addUser(admin)
    print("Users has been created\n")
    print("List of All users\n")
    print(userService.getUsers)
    print("Updateing username of Admin user\n")
    print(userService.updateUser(adminId,updatedAdmin) + "\n")
    print("Username has been updated\n")
    print(userService.getUserById(adminId) + "\n")
    print("Deleting Customer\n")
    userService.deleteUserById(customerId)
    print("Customer has been deleted\n")
    print("List of All users\n")
    print(userService.getUsers + "\n")
    print("Deleing All users\n")
    userService.deleteAllUsers()
    print("Users has been deleted\n")
    print("List of All user\n")
    print(userService.getUsers)

}
