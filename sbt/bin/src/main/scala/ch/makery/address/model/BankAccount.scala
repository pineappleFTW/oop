package ch.makery.address.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty,DoubleProperty,BooleanProperty}
import java.time.LocalDate;

class BankAccount(firstNameS:String,lastNameS:String,balanceS:Double){
  var firstName=new StringProperty(firstNameS)
  var lastName=new StringProperty(lastNameS)
  var age= ObjectProperty[Int](0)
  var address=new StringProperty("Some road")
  var interestRate= ObjectProperty[Double](0)
  var balance= ObjectProperty[Double](balanceS)
  var isActive= BooleanProperty(true)
  var accountType= new StringProperty(isAccountPremium(balanceS))
  
  def isAccountPremium(bal:Double):String={
    if (bal>50000){
      return "Premium"
    }else
      return "Basic"
  }
}
