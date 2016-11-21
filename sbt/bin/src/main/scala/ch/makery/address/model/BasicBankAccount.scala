package ch.makery.address.model
import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty,DoubleProperty,BooleanProperty}
import java.time.LocalDate;


class BasicBankAccount(firstName:String,lastName:String,age:Int,address:String,balance:Double)
              extends BankAccount(firstName,lastName,age,address,balance){
   
    val interestRate=ObjectProperty[Double](2.0)
    val accountType= new StringProperty("Basic")
  
    def this(bankAccount:BankAccount){
      this(bankAccount.firstName.value,bankAccount.lastName.value,bankAccount.age.value,bankAccount.address.value,bankAccount.balance.value)
      this.accountNum=bankAccount.accountNum
      
    }  
  
   
  
}