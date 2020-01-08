using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SabanaFlower : SabanaProp {

    public GameObject flowers;
    private Vector3 myPosition;

    // Start is called before the first frame update
    public override void MyStart() {
        myPosition = transform.position;
    }

    public override void MyOnResetPosition() {
        transform.position = myPosition;
        flowers.SetActive(true);
    }

    public override void MyOnUpdatePosition() {
        myPosition = transform.position;
    }

}
