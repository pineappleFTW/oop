package ch.makery.address.view

import ch.makery.address.model.{BankAccount,PrivilegeBankAccount,BasicBankAccount}
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView,TableColumn,Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.beans.property.{StringProperty,DoubleProperty}
import scalafx.Includes._
import scalafx.event.ActionEvent
import com.mongodb.casbah.Imports._

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
  def showSoloBankInfo(action: ActionEvent)={
    
    val selectedBankAccount = bankAccountTable.selectionModel().selectedItem.value
    if (selectedBankAccount != null) {
      val okClicked = MainApp.showBankAccountSoloOverview(selectedBankAccount)
    } else {
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(MainApp.stage)
        title = "No Bank Account Selected"
        headerText = "No Bank Account Selected"
        contentText = "Please select a bank account from the list of account."
      }.showAndWait()
    }
    
  }
  
  // Bank Account Delete
  def handleDeleteAccount(action: ActionEvent) = {
    val selectedBankAccount = bankAccountTable.selectionModel().selectedItem.value
    val selectedBankAccountIndex = bankAccountTable.selectionModel().selectedIndex.value
    val okClicked = MainApp.showBankAccountDeleteDialog(selectedBankAccount)
    
    if (MainApp.bankAccountData.length != 0 && okClicked) {
      val querydelete = MongoDBObject("accountNum" -> selectedBankAccount.accountNum.value)
      val remove = MainApp.bankAccountCollection.remove(querydelete)
      bankAccountTable.items().remove(selectedBankAccountIndex)
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
  def handleEditBankAccount(action: ActionEvent){
    
    val selectedBankAccount = bankAccountTable.selectionModel().selectedItem.value
    var num = -1
    var selectedIndex = 0
    
    if (selectedBankAccount != null) {
      for (x <- MainApp.bankAccountData) {
        num = num + 1
        if (x.accountNum == selectedBankAccount.accountNum){
          selectedIndex = num
        }
      }
      val okClicked = MainApp.showBankAccountEditDialog(selectedBankAccount)
      val query = MongoDBObject("accountNum" -> selectedBankAccount.accountNum.value)
       
      if (selectedBankAccount.accountType.value.equals("Premium")) {
         MainApp.bankAccountData.remove(selectedIndex)      
         val privBankAccount = new PrivilegeBankAccount(selectedBankAccount, selectedBankAccount.accountNum.value)
         MainApp.bankAccountData.insert(selectedIndex, privBankAccount)
         val changePriv = MongoDBObject(
             "accountNum" -> privBankAccount.accountNum.value,
             "firstName" -> privBankAccount.firstName.value,
             "lastName" -> privBankAccount.lastName.value,
             "age" -> privBankAccount.age.value,
             "address" -> privBankAccount.address.value,
             "balance" -> privBankAccount.balance.value,
             "accountType" -> privBankAccount.accountType.value)
         val result = MainApp.bankAccountCollection.update(query, changePriv)
      } else if (selectedBankAccount.accountType.value.equals("Basic")) {
        MainApp.bankAccountData.remove(selectedIndex) 
        val basicBankAccount = new BasicBankAccount(selectedBankAccount, selectedBankAccount.accountNum.value)
        MainApp.bankAccountData.insert(selectedIndex, basicBankAccount)
        val changeBasic = MongoDBObject(
            "accountNum" -> basicBankAccount.accountNum.value,
            "firstName" -> basicBankAccount.firstName.value,
            "lastName" -> basicBankAccount.lastName.value,
            "age" -> basicBankAccount.age.value,
            "address" -> basicBankAccount.address.value,
            "balance" -> basicBankAccount.balance.value,
            "accountType" -> basicBankAccount.accountType.value)
        val result = MainApp.bankAccountCollection.update(query, changeBasic)
      }
    } else {
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(MainApp.stage)
        title = "No Bank Account Selected"
        headerText = "No Bank Account Selected"
        contentText = "Please select a bank account from the list of account."
      }.showAndWait()
    }
    
  }
  
  // Create Bank Account
  def handleNewBankAccount(action: ActionEvent){
     
    val bankAccount = new BasicBankAccount(0, "", "", 0, "", 0)
    var okClicked = MainApp.showBankAccountEditDialog(bankAccount)
    if (okClicked) {
      if (bankAccount.accountType.value.equals("Premium") && bankAccount.balance.value >= 10000) {
        val privBankAccount = new PrivilegeBankAccount(bankAccount, BankAccount.generateAccountNum)
        val addPrivEntry = MongoDBObject(
            "accountNum" -> privBankAccount.accountNum.value,
            "firstName" -> bankAccount.firstName.value,
            "lastName" -> bankAccount.lastName.value,
            "age" -> bankAccount.age.value,
            "address" -> bankAccount.address.value,
            "balance" -> bankAccount.balance.value,
            "accountType" -> bankAccount.accountType.value)
        MainApp.bankAccountCollection.insert(addPrivEntry)
        MainApp.bankAccountData += privBankAccount
      } else if (bankAccount.accountType.value.equals("Basic")) {
        val basicBankAccount = new BasicBankAccount(bankAccount, BankAccount.generateAccountNum)
        val addBasicEntry = MongoDBObject(
            "accountNum" -> basicBankAccount.accountNum.value,
            "firstName" -> bankAccount.firstName.value,
            "lastName" -> bankAccount.lastName.value,
            "age" -> bankAccount.age.value,
            "address" -> bankAccount.address.value,
            "balance" -> bankAccount.balance.value,
            "accountType" -> bankAccount.accountType.value)
        MainApp.bankAccountCollection.insert(addBasicEntry)
        MainApp.bankAccountData += basicBankAccount
      }
    }
    
  }
  
  // Transaction Event
  def handleTransaction(action: ActionEvent){
    
    val selectedBankAccount = bankAccountTable.selectionModel().selectedItem.value
    if(selectedBankAccount != null){
      val okClicked = MainApp.showBankAccountTransactionDialog(selectedBankAccount)
      val query = MongoDBObject("accountNum" -> selectedBankAccount.accountNum.value)
       val transactionEntry = MongoDBObject(
           "accountNum" -> selectedBankAccount.accountNum.value,
           "firstName" -> selectedBankAccount.firstName.value,
           "lastName" -> selectedBankAccount.lastName.value,
           "age" -> selectedBankAccount.age.value,
           "address" -> selectedBankAccount.address.value,
           "balance" -> selectedBankAccount.balance.value,
           "accountType" -> selectedBankAccount.accountType.value)
       val result = MainApp.bankAccountCollection.update(query,transactionEntry)
    } else {
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(MainApp.stage)
        title = "No Bank Account Selected"
        headerText = "No Bank Account Selected"
        contentText = "Please select a bank account from the list of account."
      }.showAndWait()
    }
    
  }
  
}