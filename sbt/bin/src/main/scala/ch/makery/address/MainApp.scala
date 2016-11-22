package ch.makery.address

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.collections.{ObservableBuffer}
import ch.makery.address.model.{BankAccount, BasicBankAccount,PrivilegeBankAccount}
import scalafx.stage.{ Stage, Modality }
import ch.makery.address.view.BankAccountSoloviewController
import ch.makery.address.view.BankAccountEditDialogController
import ch.makery.address.view.BankAccountTransactionController
import scalafx.event.ActionEvent
import com.mongodb.casbah.Imports._

object MainApp extends JFXApp {
  
  // dummy data
  /*
	bankAccountData += new PrivilegeBankAccount("Jerry", "Lee", 20, "Taman Seraya", 53050.0)
  bankAccountData += new BasicBankAccount("Mun Chun", "Looi", 25, "Taman Midah", 2000.0)
  bankAccountData += new BasicBankAccount("Shaun", "Lim", 33, "Taman Midah", 1500.0)
  bankAccountData += new PrivilegeBankAccount("Rynn", "Leow", 40, "Taman Lensen", 70000.0)
  bankAccountData += new PrivilegeBankAccount("Danny", "Lim", 34, "Taman Bukit Segar", 60002.0)
  bankAccountData += new PrivilegeBankAccount("Ken", "Yeoh", 50, "Alam Damai", 53300.0)
  bankAccountData += new BasicBankAccount("Bi Lian", "Loi", 21, "Taman Desa Aman", 2020.0)
  bankAccountData += new PrivilegeBankAccount("Ronny", "Ko", 35, "Tun Hussein Onn", 132000.0)
  bankAccountData += new PrivilegeBankAccount("Dash", "Chong", 23, "Taman Midah", 142000.0)
  */

  // Database
  val mongoClient: MongoClient = MongoClient("localhost", 27017)
  val database: MongoDB = mongoClient("myBankAccount")
  val bankAccountCollection = database("BankAccount")
  var allAccount = bankAccountCollection.find()
  // Observable list of BankAccount
  val bankAccountData = new ObservableBuffer[BankAccount]()
  
  for(x <- allAccount){
    if(x.get("accountType").toString.equals("Premium")){
      val privAccount = new PrivilegeBankAccount(
          accountNum = x.get("accountNum").toString.toInt,
          firstName = x.get("firstName").toString,
          lastName = x.get("lastName").toString,
          age = x.get("age").toString.toInt,
          address = x.get("address").toString,
          balance = x.get("balance").toString.toDouble)
      bankAccountData += privAccount
    } else {
      val basicAccount = new BasicBankAccount(
          accountNum = x.get("accountNum").toString.toInt,
          firstName = x.get("firstName").toString,
          lastName = x.get("lastName").toString,
          age = x.get("age").toString.toInt,
          address = x.get("address").toString,
          balance = x.get("balance").toString.toDouble)
      bankAccountData += basicAccount
    }
  }
  
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  
  stage = new PrimaryStage {
    title = "BankApp"
    scene = new Scene {
      root = roots
    }
  }
    
  def showBankAccountOverview() = {
    val resource = getClass.getResource("view/BankAccountOverview.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  } 
    
  def showBankAccountSoloOverview(bankAccount: BankAccount): Boolean = {
    val resource = getClass.getResource("view/BankAccountSoloview.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[BankAccountSoloviewController#Controller]
    
    val dialog = new Stage(){
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene{
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.bankaccount = bankAccount
    dialog.showAndWait()
    control.okClicked
  }
    
  def showBankAccountEditDialog(bankAccount: BankAccount): Boolean = {
    val resource = getClass.getResource("view/BankAccountEditDialog.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots2 = loader.getRoot[jfxs.Parent]
    val control2 = loader.getController[BankAccountEditDialogController#Controller]
  
    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control2.dialogStage = dialog
    control2.bankAccount = bankAccount
    dialog.showAndWait()
    control2.okClicked
  }
  
  def showBankAccountTransactionDialog(bankAccount: BankAccount): Boolean = {
    val resource = getClass.getResource("view/BankAccountTransactionOverview.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots2 = loader.getRoot[jfxs.Parent]
    val control3 = loader.getController[BankAccountTransactionController#Controller]
    
    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control3.dialogStage = dialog
    control3.bankAccount = bankAccount
    dialog.showAndWait()
    control3.okClicked
  } 
  showBankAccountOverview()
}