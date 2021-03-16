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

package org.knoldus.request

import org.knoldus.db.UserRepository
import org.knoldus.model.{User, UserType}
import org.knoldus.validator.Validator
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID

class UserServiceIntegrationTest extends AnyFlatSpec{
  val validator = new Validator
  val userRepository = new UserRepository
  val user = User(None,"asbin bhadra","asbin143@gmail.com",Some("Barpeta Road"),"7988313043",UserType.Customer)
  val userService = new UserService(userRepository,validator)


  "addUser" should "return id" in{
    val result = userService.addUser(user)
    assert(Some(result).nonEmpty)
  }
  it should "throw RuntimeException as userid provided" in{
    assertThrows[RuntimeException] {
      userService.addUser(user.copy(id = Some(UUID.randomUUID())))
    }
  }

  it should "throw RuntimeException as email id invalid" in{
    assertThrows[RuntimeException] {
      userService.addUser(user.copy(email = "asbin@gma"))
    }
  }

  it should "through RuntimeException as mobile number invalid" in{
    assertThrows[RuntimeException] {
      userService.addUser(user.copy(mobileNumber = "798G3043"))
    }
  }
  it should "through RuntimeException as mobile number and email id invalid" in{
    assertThrows[RuntimeException] {
      userService.addUser(user.copy(email = "asbin@gmail",mobileNumber = "7955g77111111"))
    }
  }


  "getUsers" should "return list of user" in{
    userService.addUser(user)
    val result:List[User] = userService.getUsers
    assert(result.nonEmpty)
    userService.deleteAllUsers()
  }

  it should "return empty list" in {
    userService.deleteAllUsers()
    val result:List[User] = userService.getUsers
    assert(result.isEmpty)
  }

  "getUserById" should "return list of user" in {
    val userId = userService.addUser(user)
    val result:List[User] = userService.getUserById(userId)
    assert(result.nonEmpty)
  }

  it should "throw RuntimeException" in {
    assertThrows[RuntimeException] {
      userService.getUserById(Option(UUID.randomUUID()))
    }
    userService.deleteAllUsers()
  }


  "updateUser" should "return true" in{
    val userId = userService.addUser(user)
    val result:Boolean = userService.updateUser(userId,user.copy(userName = "Asbin123"))
    assert(result)
    userService.deleteAllUsers()
  }
  it should "through RuntimeException as email id invalid" in{
    val userId = userService.addUser(user)
    assertThrows[RuntimeException] {
      userService.updateUser(userId,user.copy(userName = "Asbin123",email = "asbin@gmail"))
    }
    userService.deleteAllUsers()
  }
  it should "through RuntimeException as mobile number invalid" in{
    val userId = userService.addUser(user)
    assertThrows[RuntimeException] {
      userService.updateUser(user.id,user.copy(userName = "Asbin123",mobileNumber = "79865g66111"))
    }
    userService.deleteAllUsers()
  }

  it should "through RuntimeException as email id and mobile number invalid" in{
    val userId = userService.addUser(user)
    assertThrows[RuntimeException] {
      userService.updateUser(user.id,user.copy(userName = "Asbin123",email = "asbin@gmail",mobileNumber = "7988421jjj333"))
    }
    userService.deleteAllUsers()
  }
  it should "return false as user not found" in{
    val userId = userService.addUser(user)
    assertThrows[RuntimeException] {
      userService.updateUser(Option(UUID.randomUUID()),user.copy(userName = "asbin143"))
    }
    userService.deleteAllUsers()
  }


  "deleteUserById" should "return true" in {
    val userId = userService.addUser(user)
    val result:Boolean = userService.deleteUserById(userId)
    assert(result)
    userService.deleteAllUsers()
  }
  it should "throw RuntimeException as user not found" in {
    val userId = userService.addUser(user)
    assertThrows[RuntimeException] {
      userService.deleteUserById(Option(UUID.randomUUID()))
    }
    userService.deleteAllUsers()
  }
  "deleteAllUser" should "return true" in {
    val userId = userService.addUser(user)
    val result:Boolean = userService.deleteAllUsers()
    assert(result)
    userService.deleteAllUsers()
  }
  it should "return false as no user in the list" in {
    val result:Boolean = userService.deleteAllUsers()
    assert(!result)
  }

}
