using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class IcebergController : MonoBehaviour {

    public GameObject shape;

    public void OnTouchDetected() {
        shape.SendMessage("OnTouchDetected");
    }

}
