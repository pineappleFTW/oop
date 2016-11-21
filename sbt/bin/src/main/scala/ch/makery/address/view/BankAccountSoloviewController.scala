package ch.makery.address.view

import ch.makery.address.model.BankAccount
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView,TableColumn,Label}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent

@sfxml
class BankAccountSoloviewController( 
  
  private val accountNumberLabel:Label,
  private val firstNameLabel:Label,
  private val lastNameLabel:Label,
  private val ageLabel:Label,
  private val addressLabel:Label,
  private val interestRateLabel:Label,
  private val balanceLabel:Label,
  private val accountTypeLabel:Label
){
  var dialogStage:Stage=null
  private var _bankaccount:BankAccount=null
  var okClicked=false
 
  
  def bankaccount=_bankaccount
  def bankaccount_=(x:BankAccount){
      _bankaccount=x
    
       accountNumberLabel.text = _bankaccount.accountNum.value.toString
       firstNameLabel.text = _bankaccount.firstName.value
       lastNameLabel.text = _bankaccount.lastName.value
       ageLabel.text = _bankaccount.age.value.toString
       addressLabel.text = _bankaccount.address.value
       interestRateLabel.text = _bankaccount.interestRate.value.toString
       balanceLabel.text = _bankaccount.balance.value.toString
       accountTypeLabel.text = _bankaccount.accountType.value
       
   }
    
  def handleOkButton(action:ActionEvent){
    okClicked=true
    dialogStage.close()
  }
  
  
  
}
  
  
  
    
    
