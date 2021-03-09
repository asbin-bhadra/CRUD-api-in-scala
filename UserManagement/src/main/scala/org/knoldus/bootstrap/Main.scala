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


object Main extends App{

    val customer = User(None,"aditya111", "aditya111@gmail.com", Option("Noida"), "7988313043",UserType.Customer)
    val admin = User(None, "rahul", "rahul1996@gmail.com", Option("Gurgaon"), "8724835905", UserType.Admin)

    val userRepository : DataAccessObject[User] = new UserRepository
    val userService = new UserService(userRepository)


    println("Creating Customer and Admin User...")
    val customerId = userService.addUser(customer)
    val adminId = userService.addUser(admin)
    println("Users has been created")
    println("List of All users")
    println(userService.getUsers)
    println("Updateing username of Admin user")
    userService.updateUserName(adminId,"rahul1996")
    println("Username has been updated")
    println(userService.getUserById(adminId))
    println("Deleting Customer")
    userService.deleteUserById(customerId)
    println("Customer has been deleted")
    println("List of All users")
    println(userService.getUsers)
    println("Deleing All users")
    userService.deleteAllUsers()
    println("Users has been deleted")
    println("List of All user")
    println(userService.getUsers)

}
