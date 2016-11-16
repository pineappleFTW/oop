package ch.makery.address.view

import ch.makery.address.model.BankAccount
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView,TableColumn,Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.beans.property.{StringProperty,DoubleProperty}
import scalafx.Includes._
import scalafx.event.ActionEvent

@sfxml
class BankAccountOverviewController ( 
    
  private val bankAccountTable: TableView[BankAccount],
  
  private val firstNameColumn: TableColumn[BankAccount,String],
  
  private val lastNameColumn: TableColumn[BankAccount,String],
  
  private val accountBalanceColumn : TableColumn[BankAccount, Double],
  
  private val accountTypeColumn : TableColumn[BankAccount,String]
)
{
  bankAccountTable.items=MainApp.bankAccountData
  
  firstNameColumn.cellValueFactory={_.value.firstName}
  lastNameColumn.cellValueFactory={_.value.lastName}
  accountBalanceColumn.cellValueFactory=(_.value.balance)
  accountTypeColumn.cellValueFactory={_.value.accountType}
  
  
   def showSoloBankInfo(action : ActionEvent)={
      val selectedBankAccount=bankAccountTable.selectionModel().selectedItem.value
      
      val okClicked=MainApp.showBankAccountSoloOverview(selectedBankAccount)
      
    }
   
   def handleDeleteAccount(action: ActionEvent)={
     val selectedBankAccount=bankAccountTable.selectionModel().selectedIndex.value
     if (selectedBankAccount>0){
       bankAccountTable.items().remove(selectedBankAccount)
     }else{
       val alert=new Alert(Alert.AlertType.Warning){
         initOwner(MainApp.stage)
         title="No Bank Account Selected"
         headerText="No Bank Account Selected"
         contentText= "Please select a bank account from the list of account."
         }.showAndWait()
     }
   }
  
}