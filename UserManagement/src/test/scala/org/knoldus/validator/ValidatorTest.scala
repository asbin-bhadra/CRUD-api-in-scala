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

package org.knoldus.validator

import org.scalatest.flatspec.AnyFlatSpec

class ValidatorTest extends AnyFlatSpec {

  val validator = new Validator
  "email" should "have alphanumeric recipient name in lowercase and/or uppercase " in {
    var result: Boolean = validator.isEmailValid("asbin@knoldus.com")
    assert(result == true)
  }

  it should "valid when with starting numbers" in {
    var result: Boolean = validator.isEmailValid("191020307002asbin@knoldus.com")
    assert(result == true)
  }
  it should "valid when recipient name has dot and underscore" in {
    var result: Boolean = validator.isEmailValid("191020307002.asbin-bhadra@knoldus.com")
    assert(result == true)
  }

  it should "invalid with missing @" in {
    var result: Boolean = validator.isEmailValid("asbinknoldus.com")
    assert(!result)
  }

  it should "invalid with space in between" in {
    var result: Boolean = validator.isEmailValid("asbin @knoldus.com")
    assert(!result)
  }

  it should "invalid with missing top level domain" in {
    var result: Boolean = validator.isEmailValid("asbin@gmail")
    assert(!result)
  }

  it should "invalid with missing main domain" in {
    var result: Boolean = validator.isEmailValid("asbin@.com")
    assert(!result)
  }

  it should "invalid with double dots after domain name" in {
    var result: Boolean = validator.isEmailValid("gaurav@knoldus..com")
    assert(!result)
  }

  "ismobilenumbervalid" should "return true " in {
    var result: Boolean = validator.isPhoneValid("7988414032")
    assert(result)
  }
  it should "return false as more than 10 digit " in {
    var result: Boolean = validator.isPhoneValid("798841764032")
    assert(!result)
  }


}
