using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SabanaAnimal : MonoBehaviour {
    private Vector3 position;

    // Start is called before the first frame update
    void Start() {
        position = transform.position;
    }

    public void OnResetPosition() {
        transform.position = position;
    }

    public void OnUpdatePosition() {
        position = transform.position;
    }

}
