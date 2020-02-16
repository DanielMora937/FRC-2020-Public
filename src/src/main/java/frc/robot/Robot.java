package frc.robot;

//import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//import edu.wpi.first.wpilibj.Victor;
//import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.TimedRobot;
//import javax.lang.model.util.ElementScanner6;
//import java.sql.Time;
// import edu.wpi.first.wpilibj.SendableChooser;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Joystick object in port 0
  private XboxController joystick;
  private XboxController joystick2;

  // Make 4 Motor Objects
  private PWMVictorSPX left1;
  private PWMVictorSPX left2;
  private PWMVictorSPX right1;
  private PWMVictorSPX right2;
  private PWMVictorSPX suckytheballs;
  private PWMVictorSPX shootythingy;
  private PWMVictorSPX shootythingy2;
  private PWMVictorSPX armmotor;
  private PWMVictorSPX roboRaise;
  private PWMVictorSPX roboRaise2;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    joystick = new XboxController(0);
    joystick2 = new XboxController(1);

    right1 = new PWMVictorSPX(0);
    right2 = new PWMVictorSPX(1);
    left1 = new PWMVictorSPX(2);
    left2 = new PWMVictorSPX(3);
    roboRaise = new PWMVictorSPX(4);
    roboRaise2 = new PWMVictorSPX(5);
    shootythingy = new PWMVictorSPX(6);
    shootythingy2 = new PWMVictorSPX(7);
    armmotor = new PWMVictorSPX(8);
    suckytheballs = new PWMVictorSPX(9);

    // may change

  }

  public void setMotors(double left, double right) {
    left1.set(left);
    left2.set(left);
    right1.set(right);
    right2.set(right);
  }

  public void roboRaise(double raise){
    roboRaise.set(raise);
    roboRaise2.set(raise);
  }

  public void conveyor(double speed){
    shootythingy.set(speed);
    shootythingy2.set(speed);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  public static void wait(int ms){
    try{
      Thread.sleep(ms);
    } catch(InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }
  
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here

      /**
       * Plan 1 is going to deposit the balls in the goal 
       * Plan 2 is to go forward and turn around 
       * Plan 3 is to go to the trench 
       * Plan 4 is to block the opponent loading zone
       */

      var plan = 1;
      // The plan #
      var pos = 1;
      // position of the robot. 1 is closest to the goal, 3 is farthest
      var wait = 1;
      // amount of ms waiting for other robots' autonomous
      var between = 1;
      // milliseconds of driving to get from one position's start place to the next
      var forward = 1;
      // the amount of ms to move forward
      var turn = 1;
      // the amount of time turning to rotate 90 degrees (in milliseconds)
      var toGoal = 1;
      // the milliseconds of driving from the line to the goal
      var shoot = 1;
      // the milliseconds of conveyor action to get the balls in the goal

      switch (plan) {
        case 1:
          // wait for other robots
          wait(wait);
          // turn right
          setMotors(-1, 1);
          wait(turn);
          // go to goal area
          setMotors(1, 1);
          wait(between * (pos - 1));
          // turn to face goal area
          setMotors(-1, 1);
          wait(turn);
          // move up to goal
          setMotors(1, 1);
          wait(toGoal);
          // unload the balls
          setMotors(0, 0);
          conveyor(1);
          wait(shoot);
          // move backwards
          conveyor(0);
          setMotors(-1, -1);
          wait(forward);
          break;
        case 2:
          // go forward
          setMotors(1, 1);
          wait(forward);
          // turn left
          setMotors(-1, 1);
          wait(turn);
          break;
        case 3:
          // go forward
          setMotors(1, 1);
          wait(forward);
          // turn left
          setMotors(-1, 1);
          wait(turn);
          // go to trench area
          setMotors(1, 1);
          wait(between * (pos - 1));
          break;
        case 4:
          // Next case
          break;
        }
        // stop motors
        setMotors(0, 0);
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */

  public double power;
  public double turn;
  public double theta;
  //public double ArmToggle;

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    {// Controller 1 Code
    if(joystick.getTriggerAxis(Hand.kLeft) > 0.6){
      setMotors(0, 0);
      //hard break
    } else {
      power = joystick.getY(Hand.kLeft);
      turn = joystick.getX(Hand.kRight);
      theta = Math.atan(power/turn);
      power *= Math.pow(Math.sin(theta), 2);
      turn *= Math.pow(Math.cos(theta), 2);
      // incorporates forward/backward speed with turning request
      setMotors(power + turn, power - turn);
      //movement code 
    }
    if(joystick.getYButton()){
      armmotor.set(-0.7);
    } else if(joystick.getAButton()){
      armmotor.set(0.7);
    }
    if(joystick.getBumper(Hand.kLeft)){
      roboRaise(-0.7);
    } else if(joystick.getBumper(Hand.kRight)){
      roboRaise(0.7);
    }}

    {// Controller 2 Code
    //joystick.getTriggerAxis(Hand.kRight) > 0.6
    if(joystick2.getXButton()){
      suckytheballs.set(1);
    }
    if(joystick2.getBumper(Hand.kLeft)){
      conveyor(-0.4);
    } else if(joystick2.getBumper(Hand.kRight)){
      conveyor(0.4);
    } else if(joystick2.getTriggerAxis(Hand.kLeft) > 0.7){
      conveyor(-1);
    } else if(joystick2.getTriggerAxis(Hand.kRight) > 0.7){
      conveyor(1);
    }}
  }

  /**
   * This function is called periodically during test mode.
   */ 

  @Override

  public void testPeriodic() {}
}
