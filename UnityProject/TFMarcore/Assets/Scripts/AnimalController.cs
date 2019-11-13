using GoogleARCore;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AnimalController : MonoBehaviour {

    private DetectedPlane detectedPlane;
    private GameObject animalInstance;

    private float timeSinceLastAnimal;
    private readonly float maxTime = 10f;

    public GameObject[] animalModels;
    // Start is called before the first frame update
    void Start() {

    }

    // Update is called once per frame
    void Update() {

    }
}
