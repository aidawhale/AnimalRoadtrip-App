using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ShapeController : MonoBehaviour {

    public enum Shapes {
        Triangle,
        Square,
        Pentagon,
        Circle,
        Star
    };
    public Shapes shape;

    public GameObject Pingu;
    private GameObject map;
    private float rotation;
    private int shapeNumber = -1;

    private void Start() {
        map = GameObject.FindGameObjectWithTag("Map");
        switch(shape) {
            case Shapes.Triangle:
                shapeNumber = 1;
                break;
            case Shapes.Square:
                shapeNumber = 2;
                break;
            case Shapes.Pentagon:
                shapeNumber = 3;
                break;
            case Shapes.Circle:
                shapeNumber = 4;
                break;
            case Shapes.Star:
                shapeNumber = 5;
                break;
        };
    }

    void Update() {
        rotation = 50 * Time.deltaTime;
        this.transform.Rotate(0, 0, rotation);
    }

    private void OnTriggerEnter(Collider collision) {

        if (collision.gameObject.name == "Pingu") {
            map.SendMessage("OnShapeCollision", shapeNumber);
        }
    }

    public void OnTouchDetected() {
        Pingu.SendMessage("OnMoveTowards", transform.position);
    }
}
