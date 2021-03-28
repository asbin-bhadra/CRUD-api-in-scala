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

import org.knoldus.database.UserTable
import org.knoldus.repository.UserRepository
import org.knoldus.model.{User, UserType}
import org.knoldus.validator.Validator
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration._

class UserServiceIntegrationTest extends AnyFlatSpec{
  val validator = new Validator
  val userTable = new UserTable
  val userRepository = new UserRepository(userTable)
  val user = User(None,"asbin bhadra","asbin143@gmail.com",Some("Barpeta Road"),"7988313043",UserType.Customer)
  val userService = new UserService(userRepository,validator)
//
//
  "addUser" should "return id" in{
    val result = Await.result(userService.addUser(user),5 seconds)
    assert(Some(result).nonEmpty)
    Await.result(userService.deleteAllUsers(),5 seconds)
  }
  it should "throw RuntimeException as userid provided" in{
    assertThrows[RuntimeException] {
      Await.result(userService.addUser(user.copy(id = Some(UUID.randomUUID()))),5 seconds)
    }
  }
//
  it should "throw RuntimeException as email id invalid" in{
    assertThrows[RuntimeException] {
      Await.result(userService.addUser(user.copy(email = "asbin@gma")),5 seconds)
    }
  }

  it should "through RuntimeException as mobile number invalid" in{
    assertThrows[RuntimeException] {
      Await.result(userService.addUser(user.copy(mobileNumber = "798G3043")),5 seconds)
    }
  }
  it should "through RuntimeException as mobile number and email id invalid" in{
    assertThrows[RuntimeException] {
      Await.result(userService.addUser(user.copy(email = "asbin@gmail",mobileNumber = "7955g77111111")), 5 seconds)
    }
  }


  "getUsers" should "return list of user" in{
    Await.result(userService.addUser(user),5 seconds)
    val result:List[User] = Await.result(userService.getUsers,5 seconds)
    assert(result.nonEmpty)
    userService.deleteAllUsers()
  }

  it should "return empty list" in {
    Await.result(userService.deleteAllUsers(),5 seconds)
    val result:List[User] = Await.result(userService.getUsers,5 seconds)
    assert(result.isEmpty)
  }

  "getUserById" should "return list of user" in {
    val userId = Await.result(userService.addUser(user),5 seconds)
    val result:List[User] = Await.result(userService.getUserById(userId),5 seconds)
    assert(result.nonEmpty)
  }

  it should "throw RuntimeException" in {
    assertThrows[RuntimeException] {
      Await.result(userService.getUserById(Option(UUID.randomUUID())),5 seconds)
    }
    userService.deleteAllUsers()
  }


  "updateUser" should "return true" in{
    val userId = Await.result(userService.addUser(user),5 seconds)
    val result:Boolean = Await.result(userService.updateUser(userId,user.copy(userName = "Asbin123")), 5 seconds)
    assert(result)
    userService.deleteAllUsers()
  }
  it should "through RuntimeException as email id invalid" in{
    val userId = Await.result(userService.addUser(user), 5 seconds)
    assertThrows[RuntimeException] {
      Await.result(userService.updateUser(userId,user.copy(userName = "Asbin123",email = "asbin@gmail")), 5 seconds)
    }
    userService.deleteAllUsers()
  }
  it should "through RuntimeException as mobile number invalid" in{
    val userId = Await.result(userService.addUser(user), 5 seconds)
    assertThrows[RuntimeException] {
      Await.result(userService.updateUser(user.id,user.copy(userName = "Asbin123",mobileNumber = "79865g66111")), 5 seconds)
    }
    userService.deleteAllUsers()
  }

  it should "through RuntimeException as email id and mobile number invalid" in{
    val userId = Await.result(userService.addUser(user), 5 seconds)
    assertThrows[RuntimeException] {
      Await.result(userService.updateUser(user.id,user.copy(userName = "Asbin123",email = "asbin@gmail",mobileNumber = "7988421jjj333")), 5 seconds)
    }
    userService.deleteAllUsers()
  }
  it should "return false as user not found" in{
    val userId = Await.result(userService.addUser(user), 5 seconds)
    val result = Await.result(userService.updateUser(Option(UUID.randomUUID()),user.copy(userName = "asbin143")),5 seconds)
    assert(!result)
    userService.deleteAllUsers()
  }


  "deleteUserById" should "return true" in {
    val userId = Await.result(userService.addUser(user),5 seconds)
    val result:Boolean = Await.result(userService.deleteUserById(userId),5 seconds)
    assert(result)
    userService.deleteAllUsers()
  }
  it should "return false" in {
    val result:Boolean = Await.result(userService.deleteUserById(Option(UUID.randomUUID())), 5 seconds)
    assert(!result)
    userService.deleteAllUsers()
  }

  "deleteAllUser" should "return true" in {
    val userId = Await.result(userService.addUser(user),5 seconds)
    val result:Boolean = Await.result(userService.deleteAllUsers(),5 seconds)
    assert(result)
    userService.deleteAllUsers()
  }
  it should "return false as no user in the list" in {
    val result:Boolean = Await.result(userService.deleteAllUsers(),5 seconds)
    assert(!result)
  }

}
