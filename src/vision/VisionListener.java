package vision;

import java.util.List;
import java.util.stream.Collectors;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.*;

public class VisionListener implements VisionRunner.Listener<GripPipeline>{

	private static Object visionMutex = new Object();
	
	private static VisionResult result;
	
	public static int numResults;
	public class VisionResult{
		public double x;
		public double y;
		public boolean found;
	}
	
	public static boolean newResult;
	
	public static double extremeVal;
	
	public static VisionResult getResult(){
		synchronized(visionMutex){
			newResult = false;
			return result;
		}
	}
	
	private CvSource outputStream;
	private CvSink input;
	public VisionListener(CvSource outputStream, CvSink input){
		this.outputStream = outputStream;
		this.input = input;
	}
	@Override
	public void copyPipelineOutputs(GripPipeline pipeline) {
		
		Mat img = new Mat(160, 120, 0);
		input.grabFrame(img);
		
		List<MatOfPoint> matList = pipeline.convexHullsOutput();
		
		
		List<Point> centerpoints = matList.stream()
			.map(Imgproc::boundingRect)
			.peek((Rect a)->Imgproc.rectangle(img, a.tl(), a.br(), new Scalar(0,0,255)))
			.sorted((Rect a, Rect b)->(int)(b.area()-a.area()))
			.limit(2)
			.peek((Rect a)->Imgproc.rectangle(img, a.tl(), a.br(), new Scalar(255,0,0)))
			.map((Rect a)->new Point(a.x+(a.width/2),a.y+(a.height)/2))
			.collect(Collectors.toList());
		
		
	
		if(centerpoints.size()>=2){
			double ext = img.width()/2;
			for(Point i : centerpoints)
				if(Math.abs(i.x-img.width()/2)>Math.abs(ext-img.width()/2)) ext = i.x;
			extremeVal = ext-img.width()/2;
		}
		
			centerpoints.stream()
			.reduce((Point a, Point b)->(new Point((a.x+b.x)/2,(a.y+b.y)/2)))
			.ifPresent((Point p)->
			{
				VisionResult r = new VisionResult();
				r.found = true;
				r.x = ((double)p.x)/img.width();
				r.y = ((double)p.y)/img.height();
			
				SmartDashboard.putNumber("VisionX", r.x);
				Imgproc.circle(img, p, 5, new Scalar(0,255,255));
				
				synchronized(visionMutex){
					result = r;
					numResults = matList.size();
					newResult = true;
				}
				
			});
		
		outputStream.putFrame(img);
		img.release();
		
	}
	
}
