using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TreasureAnimalController : MonoBehaviour {

    private int x, y, z;

    public void OnTouchDetected() {

        x = Random.Range(0, 60);
        y = Random.Range(0, 30);
        z = Random.Range(0, 30);

        transform.Rotate(x, y, z);
    }
}
