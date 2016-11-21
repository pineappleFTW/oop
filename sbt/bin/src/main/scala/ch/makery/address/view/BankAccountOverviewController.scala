package ch.makery.address.view

import ch.makery.address.model.{BankAccount,PrivilegeBankAccount,BasicBankAccount}
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView,TableColumn,Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.beans.property.{StringProperty,DoubleProperty}
import scalafx.Includes._
import scalafx.event.ActionEvent

@sfxml
class BankAccountOverviewController(
    
  private val bankAccountTable: TableView[BankAccount],
  private val accountNumberColumn: TableColumn[BankAccount, Int],
  private val firstNameColumn: TableColumn[BankAccount, String],
  private val lastNameColumn: TableColumn[BankAccount, String],
  private val accountBalanceColumn: TableColumn[BankAccount, Double],
  private val accountTypeColumn: TableColumn[BankAccount, String]
  
){
  
  bankAccountTable.items = MainApp.bankAccountData
  
  accountNumberColumn.cellValueFactory = {_.value.accountNum}
  firstNameColumn.cellValueFactory = {_.value.firstName}
  lastNameColumn.cellValueFactory = {_.value.lastName}
  accountBalanceColumn.cellValueFactory = (_.value.balance)
  accountTypeColumn.cellValueFactory = {_.value.accountType}

  // Show Bank Account
  def showSoloBankInfo(action: ActionEvent) = {
    val selectedBankAccount = bankAccountTable.selectionModel().selectedItem.value
    val okClicked = MainApp.showBankAccountSoloOverview(selectedBankAccount)
  }
  
  // Bank Account Delete
  def handleDeleteAccount(action: ActionEvent) = {
    val selectedBankAccount = bankAccountTable.selectionModel().selectedIndex.value
    if (MainApp.bankAccountData.length != 0){
      bankAccountTable.items().remove(selectedBankAccount)
    } else {
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(MainApp.stage)
        title = "No Bank Account Selected"
        headerText = "No Bank Account Selected"
        contentText = "Please select a bank account from the list of account."
      }.showAndWait()
    }
  }
  
  // Bank Account Edit
  def handleEditBankAccount(action:ActionEvent){
    val selectedBankAccount = bankAccountTable.selectionModel().selectedItem.value
    var num = -1
    var selectedIndex = 0
    for (x <- MainApp.bankAccountData) {
      num = num + 1
      if (x.accountNum == selectedBankAccount.accountNum) {
        selectedIndex = num
      }
    }
    if (selectedBankAccount != null) {
      val okClicked = MainApp.showBankAccountEditDialog(selectedBankAccount)
      if (selectedBankAccount.accountType.value.equals("Premium")) {
        MainApp.bankAccountData.remove(selectedIndex)      
        val privBankAccount=new PrivilegeBankAccount(selectedBankAccount)
        MainApp.bankAccountData.insert(selectedIndex, privBankAccount)
      } else if (selectedBankAccount.accountType.value.equals("Basic")){
        MainApp.bankAccountData.remove(selectedIndex) 
        val basicBankAccount = new BasicBankAccount(selectedBankAccount)
        MainApp.bankAccountData.insert(selectedIndex, basicBankAccount)
      }
    }
  }
  
  // Create Bank Account
  def handleNewBankAccount(action: ActionEvent){
    val bankAccount = new BasicBankAccount("", "", 0, "", 0)
    var okClicked = MainApp.showBankAccountEditDialog(bankAccount)
    if (okClicked) {
      if (bankAccount.accountType.value.equals("Premium") && bankAccount.balance.value >= 10000) {
        val privBankAccount = new PrivilegeBankAccount(bankAccount)
        MainApp.bankAccountData += privBankAccount
      } else if (bankAccount.accountType.value.equals("Basic")) {
        val basicBankAccount = new BasicBankAccount(bankAccount)
        MainApp.bankAccountData += basicBankAccount
      }
    }
  }
  
  // Transaction Event
  def handleTransaction(action: ActionEvent){
    val selectedBankAccount = bankAccountTable.selectionModel().selectedItem.value
    if (selectedBankAccount != null) {
      val okClicked = MainApp.showBankAccountTransactionDialog(selectedBankAccount)
    }
  }
}