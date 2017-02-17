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
import edu.wpi.first.wpilibj.vision.*;

public class VisionListener implements VisionRunner.Listener<GripPipeline>{

	private static Object visionMutex = new Object();
	
	private static VisionResult result;
	
	public class VisionResult{
		public double x;
		public double y;
		public boolean found;
	}
	
	public static boolean newResult;
	
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
		matList.stream()
			.map(Imgproc::boundingRect)
			.peek((Rect a)->Imgproc.rectangle(img, a.tl(), a.br(), new Scalar(0,0,255)))
			.sorted((Rect a, Rect b)->(int)(b.area()-a.area()))
			.limit(2)
			.peek((Rect a)->Imgproc.rectangle(img, a.tl(), a.br(), new Scalar(255,0,0)))
			.map((Rect a)->new Point(a.x+(a.width/2),a.y+(a.height)/2))
			.reduce((Point a, Point b)->(new Point((a.x+b.x)/2,(a.y+b.y)/2)))
			.ifPresent((Point p)->
			{
				VisionResult r = new VisionResult();
				r.found = true;
				r.x = ((double)p.x)/img.width();
				r.y = ((double)p.y)/img.height();
				
				Imgproc.circle(img, p, 5, new Scalar(0,255,255));
				
				synchronized(visionMutex){
					result = r;
					newResult = true;
				}
				
			});
		
		outputStream.putFrame(img);
		img.release();
		
	}
	
}
