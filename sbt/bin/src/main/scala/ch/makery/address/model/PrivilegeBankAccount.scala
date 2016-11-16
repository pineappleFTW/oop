package ch.makery.address.model
import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty,DoubleProperty,BooleanProperty}
import java.time.LocalDate;


class PrivilegeBankAccount(firstName:String,lastName:String,balance:Double)
              extends BankAccount(firstName,lastName,balance){
    interestRate=ObjectProperty[Double](5.0)
    accountType= new StringProperty("Premium")
  
}