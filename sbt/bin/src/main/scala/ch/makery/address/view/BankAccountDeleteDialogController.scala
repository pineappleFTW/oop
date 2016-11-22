package ch.makery.address.view

import ch.makery.address.model.BankAccount
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView,TableColumn,Label,TextField,Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent
import com.mongodb.casbah.Imports._

@sfxml
class BankAccountDeleteDialogController(

  private val accountNumberLabel:Label,
  private val firstNameLabel:Label,
  private val lastNameLabel:Label
  
){
  
  var dialogStage:Stage=null
  private var _bankAccount:BankAccount=null
  var okClicked=false
      
  def bankAccount=_bankAccount
  def bankAccount_=(x:BankAccount){
    _bankAccount=x
      
    accountNumberLabel.text = _bankAccount.accountNum.value.toString
    firstNameLabel.text = _bankAccount.firstName.value
    lastNameLabel.text = _bankAccount.lastName.value
  }
    
  def handleOkButton(action:ActionEvent){
    okClicked=true
    dialogStage.close()
  }
    
  def handleCancelButton(action:ActionEvent){
    dialogStage.close()
  }
  
}