using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PinguMapController : MonoBehaviour {

    public GameObject Pingu;
    public Material hatColor1;
    public Material hatColor2;

    public GameObject[] PinguFriends;

    public GameObject Campfire;
    private ParticleSystem fire;

    private int[] shapeOrder;


    // Start is called before the first frame update
    void Start() {
        fire = Campfire.GetComponentInChildren<ParticleSystem>();
        SetScene();
    }

    private void SetScene() {
        // TO DO
    }
}
