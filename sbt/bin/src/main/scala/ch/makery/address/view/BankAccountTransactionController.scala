package ch.makery.address.view

import ch.makery.address.model.BankAccount
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView,TableColumn,Label,TextField,Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent

@sfxml
class BankAccountTransactionController (
  
  private val accountNumberLabel:Label,
  private val firstNameLabel:Label,
  private val lastNameLabel:Label,
  private val balanceLabel:Label,
  private val accNumField:TextField,
  private val transferField:TextField,
  private val depositField:TextField,
  private val withdrawField:TextField
  
 ){
  var dialogStage:Stage=null
  private var _bankAccount:BankAccount=null
  var okClicked=false
 
  
  def bankAccount=_bankAccount
  def bankAccount_=(x:BankAccount){
      _bankAccount=x
    
       accountNumberLabel.text=_bankAccount.accountNum.value.toString
       firstNameLabel.text = _bankAccount.firstName.value
       lastNameLabel.text = _bankAccount.lastName.value
       balanceLabel.text = _bankAccount.balance.value.toString
       
   }
  
  def handleTransferButton(action:ActionEvent){
    var selectedBankAccount:BankAccount=null
    for(x<-MainApp.bankAccountData){
      if(accNumField.text.value.toInt==x.accountNum.value){
        selectedBankAccount=x
      }
    }
    if(selectedBankAccount==null){
      val alert=new Alert(Alert.AlertType.Warning){
             initOwner(MainApp.stage)
             title="No such account in database "
             headerText="Account not found "
             contentText= "Please create this account"
             }.showAndWait()
    }else {
      if(selectedBankAccount.accountNum.value!=_bankAccount.accountNum.value && selectedBankAccount!=null){
      if(_bankAccount.balance.value-transferField.text.value.toDouble >=0){
      _bankAccount.balance.value = _bankAccount.balance.value - transferField.text.value.toDouble
      selectedBankAccount.balance.value=selectedBankAccount.balance.value + transferField.text.value.toDouble
         }else
        {
             val alert=new Alert(Alert.AlertType.Warning){
             initOwner(MainApp.stage)
             title="Insufficient Balance to carry out transfer"
             headerText="Insufficient Balance "
             contentText= "Insufficient Balance in account"
             }.showAndWait()
        }
    }else{
      val alert=new Alert(Alert.AlertType.Warning){
             initOwner(MainApp.stage)
             title="You cannot transfer to your own account"
             headerText="Error, cannot transfer to own account "
             contentText= "Please choose another bank account"
             }.showAndWait()
    }
    
    }
    
    
    
    balanceLabel.text = _bankAccount.balance.value.toString
  }
  
  def handleDepositButton(action:ActionEvent){
    _bankAccount.balance.value = _bankAccount.balance.value + depositField.text.value.toDouble
    balanceLabel.text = _bankAccount.balance.value.toString
  }
  
  def handleWithdrawButton(action:ActionEvent){
    if(_bankAccount.balance.value-withdrawField.text.value.toDouble>=0){
      _bankAccount.balance.value = _bankAccount.balance.value - withdrawField.text.value.toDouble 
    }else
    {
         val alert=new Alert(Alert.AlertType.Warning){
         initOwner(MainApp.stage)
         title="No Balance Left"
         headerText="No Balance in account"
         contentText= "There is no money in the bank account"
         }.showAndWait()
    }
    
     balanceLabel.text = _bankAccount.balance.value.toString
  }
  
  def handleOkButton(action:ActionEvent){
    okClicked=true
    dialogStage.close()
  }
  
   def handleCancel(action:ActionEvent){
    dialogStage.close()
  }
   

}