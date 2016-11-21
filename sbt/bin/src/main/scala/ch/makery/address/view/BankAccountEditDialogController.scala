package ch.makery.address.view

import ch.makery.address.model.{BankAccount,BasicBankAccount,PrivilegeBankAccount}
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView,TableColumn,Label,TextField,Alert,RadioButton}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent

@sfxml
class BankAccountEditDialogController(
    
  private val firstNameField: TextField,
  private val lastNameField: TextField,
  private val ageField: TextField,
  private val addressField: TextField,
  private val balanceField: TextField,
  private val privilegeRadioButton: RadioButton,
  private val basicRadioButton: RadioButton
  
){
  
  var dialogStage: Stage = null
  private var _bankAccount: BankAccount = null
  var okClicked = false
  
  def bankAccount=_bankAccount
  def bankAccount_=(x:BankAccount){
    _bankAccount=x
    
    firstNameField.text = _bankAccount.firstName.value
    lastNameField.text = _bankAccount.lastName.value
    ageField.text = _bankAccount.age.value.toString
    addressField.text = _bankAccount.address.value
    balanceField.text = _bankAccount.balance.value.toString
    if (_bankAccount.accountType.value.equals("Premium")){
      privilegeRadioButton.selected = true
    } else if (_bankAccount.accountType.value.equals("Basic")){
      basicRadioButton.selected = true
    }
  }
  
  def handleOk(action: ActionEvent): Unit = {
    if (isInputValid()) {
      _bankAccount.firstName <== firstNameField.text
      _bankAccount.lastName <== lastNameField.text
      _bankAccount.age.value = ageField.text.value.toInt
      _bankAccount.address.value = addressField.text.value
      _bankAccount.balance.value = balanceField.text.value.toDouble
      if(privilegeRadioButton.selected.value == true && basicRadioButton.selected.value == false && _bankAccount.balance.value >= 50000){
        _bankAccount.accountType.value = "Premium"
        okClicked = true;
        dialogStage.close()
      } else if (basicRadioButton.selected.value == true && privilegeRadioButton.selected.value == false) {
        _bankAccount.accountType.value = "Basic"
        okClicked = true;
        dialogStage.close()
      } else {
        val alert = new Alert(Alert.AlertType.Error){
        initOwner(dialogStage)
        title = "Insufficient balance for privilege"
        headerText = "The minimum amount for a privilege account is 50k"
        contentText = "Insufficient balance"
      }.showAndWait()
        okClicked = false
      }
    }
  }
  
  def handleCancel(action: ActionEvent){
    dialogStage.close()
  }
  
  def nullChecking(x: String) = x == null || x.length == 0
  def nullCheckingInt(x: Int) = x.equals(null)
  def nullCheckingDouble(x: Double) = x.equals(null)
  
  def isInputValid(): Boolean = {
    var errorMessage=""
    
    if (nullChecking(firstNameField.text.value))
      errorMessage += "No valid first name!\n"
    if (nullChecking(lastNameField.text.value))
      errorMessage += "No valid last name!\n"
    if(ageField.text.value.toInt==0)
       errorMessage += ("Please enter a valid age !\n")
    if(nullChecking(addressField.text.value))
       errorMessage += ("No valid address!\n")
    if(balanceField.text.value.toDouble<10.0)
      errorMessage += ("Minimum balance is RM10\n")
    if (errorMessage.length() == 0){
      return true
    } else {
      val alert = new Alert(Alert.AlertType.Error){
        initOwner(dialogStage)
        title = "Invalid Fields"
        headerText = "Please correct invalid fields"
        contentText = errorMessage
      }.showAndWait()
      return false;
    }
  }
}
