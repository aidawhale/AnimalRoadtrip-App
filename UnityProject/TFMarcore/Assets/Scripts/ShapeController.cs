using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ShapeController : MonoBehaviour {

    public GameObject Pingu;
    private float rotation;

    void Update() {
        rotation = 50 * Time.deltaTime;
        this.transform.Rotate(0, 0, rotation);
    }

    public void OnTouchDetected() {
        Pingu.SendMessage("OnMoveTowards", transform.position);
    }
}
