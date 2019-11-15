using GoogleARCore;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AnimalController : MonoBehaviour {
    
    // Start is called before the first frame update
    void Start() {

    }

    // Update is called once per frame
    void Update() {

    }

    public void OnTouchDetected() {
        transform.Rotate(30, 30, 30);
        for(int i = 0; i<500; i++) {

        }
        Destroy(this);
    }
}
