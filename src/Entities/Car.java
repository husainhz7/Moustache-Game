package Entities;

import states.GameState;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

public class Car {
	
	CompoundCollisionShape shape;
	public static Node vehicle;
	public static VehicleControl control;
	
	public static boolean inCar = false;
	
	public Car(){
		shape =new CompoundCollisionShape();
		BoxCollisionShape box = new BoxCollisionShape(new Vector3f(1.2f, 0.5f, 2.4f));
		shape.addChildShape(box, new Vector3f(0, 1, 0));
		vehicle = new Node("vehicle");
		control = new VehicleControl(shape, 400);
		vehicle.addControl(control);
		float stiffness = 60.0f;//200=f1 car
		float compValue = .3f; //(should be lower than damp)
		float dampValue = .4f;
		control.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
		control.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
		control.setSuspensionStiffness(stiffness);
		control.setMaxSuspensionForce(10000.0f);
		//Create four wheels and add them at their locations
        Vector3f wheelDirection = new Vector3f(0, -1, 0); // was 0, -1, 0
        Vector3f wheelAxle = new Vector3f(-1, 0, 0); // was -1, 0, 0
        float radius = 0.5f;
        float restLength = 0.3f;
        float yOff = 0.5f;
        float xOff = 1f;
        float zOff = 2f;

        Cylinder wheelMesh = new Cylinder(16, 16, radius, radius * 0.6f, true);
        Material mat = new Material(GameState.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", ColorRGBA.Red);

        Node node1 = new Node("wheel 1 node");
        Geometry wheels1 = new Geometry("wheel 1", wheelMesh);
        node1.attachChild(wheels1);
        wheels1.rotate(0, FastMath.HALF_PI, 0);
        wheels1.setMaterial(mat);
        control.addWheel(node1, new Vector3f(-xOff, yOff, zOff), wheelDirection, wheelAxle, restLength, radius, true);

        Node node2 = new Node("wheel 2 node");
        Geometry wheels2 = new Geometry("wheel 2", wheelMesh);
        node2.attachChild(wheels2);
        wheels2.rotate(0, FastMath.HALF_PI, 0);
        wheels2.setMaterial(mat);
        control.addWheel(node2, new Vector3f(xOff, yOff, zOff), wheelDirection, wheelAxle, restLength, radius, true);

        Node node3 = new Node("wheel 3 node");
        Geometry wheels3 = new Geometry("wheel 3", wheelMesh);
        node3.attachChild(wheels3);
        wheels3.rotate(0, FastMath.HALF_PI, 0);
        wheels3.setMaterial(mat);
        control.addWheel(node3, new Vector3f(-xOff, yOff, -zOff), wheelDirection, wheelAxle, restLength, radius, false);

        Node node4 = new Node("wheel 4 node");
        Geometry wheels4 = new Geometry("wheel 4", wheelMesh);
        node4.attachChild(wheels4);
        wheels4.rotate(0, FastMath.HALF_PI, 0);
        wheels4.setMaterial(mat);
        control.addWheel(node4, new Vector3f(xOff, yOff, -zOff), wheelDirection, wheelAxle, restLength, radius, false);

        vehicle.attachChild(node1);
        vehicle.attachChild(node2);
        vehicle.attachChild(node3);
        vehicle.attachChild(node4);
        GameState.app.getRootNode().attachChild(vehicle);
        GameState.bullet.getPhysicsSpace().add(control);
        inCar = true;
	}
	
	public void remove(){
		GameState.app.getRootNode().detachChild(vehicle);
		GameState.bullet.getPhysicsSpace().remove(control);
		inCar = false;
	}
}
