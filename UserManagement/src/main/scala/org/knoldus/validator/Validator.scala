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

class Validator {

  // Check if an email ID is valid or not
  def isEmailValid(emailId: String): Boolean = {
    // Regular Expression for email id
    val regex = """([\w\.!#$%&*+/=?^_`{|}~-]+)@([\w]+)([\.]{1}[\w]+)+"""
    emailId.matches(regex)
  }

  // Check if a mobile number is valid or not
  def isPhoneValid(number : String): Boolean={
    // Regular Expression for mobile number
    val regex = """([\d]){10}"""
    number.matches(regex)
  }
}
