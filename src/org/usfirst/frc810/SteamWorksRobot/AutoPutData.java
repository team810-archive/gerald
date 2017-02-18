package org.usfirst.frc810.SteamWorksRobot;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPutData {

	private static ScheduledThreadPoolExecutor executor= new ScheduledThreadPoolExecutor(1);
	
	private static List<SupplierPair<Double>> numbers = new CopyOnWriteArrayList<>();
	private static List<SupplierPair<Boolean>> bools = new CopyOnWriteArrayList<>();
	
	static{
		executor.scheduleWithFixedDelay(()->{
			try{
			numbers.forEach(a->{
				//System.out.println("Putting on SmartDashboard:"+a.name+", "+a.supplier.get());
				SmartDashboard.putNumber(a.name, a.supplier.get());
			});
			bools.forEach(a->SmartDashboard.putBoolean(a.name, a.supplier.get()));
			}catch(Exception e){
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				pw.flush();
				pw.close();
				edu.wpi.first.wpilibj.DriverStation.reportError("Exception in autoPutData: " + sw.toString(), false);
				System.err.println("Exception in autoPutData: ");
				e.printStackTrace(System.err);
				
			}
		}, 0, 50, TimeUnit.MILLISECONDS);
	}
	
	
	
	private static class SupplierPair<K>{
		String name;
		Supplier<K> supplier;
	}
	
	public static void addNumber(String name, Supplier<Number> number){
		SupplierPair<Double> sp = new SupplierPair<>();
		sp.name = name;
		sp.supplier = ()->(number.get().doubleValue());
		numbers.add(sp);
	}
	
	public static void addBoolean(String name, Supplier<Boolean> bool){
		SupplierPair<Boolean> sp = new SupplierPair<>();
		sp.name= name;
		sp.supplier = bool;
		bools.add(sp);
	}
}
