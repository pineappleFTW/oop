package ch.makery.address.model

import ch.makery.address.MainApp
import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty,DoubleProperty,BooleanProperty}
import java.time.LocalDate;
import scala.util.Random

abstract class BankAccount(accountNumS:Int, firstNameS: String, lastNameS: String, ageS: Int, addressS: String, balanceS: Double) {
  
  var accountNum = ObjectProperty[Int](accountNumS)
  var firstName = new StringProperty(firstNameS)
  var lastName = new StringProperty(lastNameS)
  var age = ObjectProperty[Int](ageS)
  var address = new StringProperty(addressS)
  val interestRate: ObjectProperty[Double]
  var balance = ObjectProperty[Double](balanceS)
  val accountType: StringProperty
  
}

object BankAccount {
  
  def generateAccountNum: Int = {
    var rand = 0
    while (rand < 1000){
      rand = new Random().nextInt(10000)
      for (x <- MainApp.bankAccountData) {
        if (x.accountNum == rand){
          rand = new Random().nextInt(10000)
        }
      }
    }
    return rand
  }
  
}