package ch.makery.address.model
import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty,DoubleProperty,BooleanProperty}
import java.time.LocalDate;


class BasicBankAccount(firstName:String,lastName:String,balance:Double)
              extends BankAccount(firstName,lastName,balance){
    interestRate=ObjectProperty[Double](2.0)
    accountType= new StringProperty("Basic")
  
}