package unlekker.test;

import processing.core.PApplet;

public class MainTest extends PApplet {

	public void setup() {
		
	}
	
	public void draw() {
		
	}
	
	public void keyPressed() {
	}
	
	public static void main(String[] args) {
		String sketch[] =new String [] {
				"unlekker.test.FlattenTest",
				"unlekker.test.GUITest",
				"unlekker.test.UPShapeToolTest",
				"unlekker.test.ModelBuilderTest"
		};
		
//		PApplet.main(new String[] {"unlekker.test.BezPatchTest"});
//		PApplet.main(new String[] {"unlekker.test.MainTest"});
//		PApplet.main(new String[] {"unlekker.test.UPShapeToolTest"});
//		PApplet.main(new String[] {"unlekker.test.UGeometryTransformerTest"});
		PApplet.main(new String[] {sketch[3]});
		
	}

}
