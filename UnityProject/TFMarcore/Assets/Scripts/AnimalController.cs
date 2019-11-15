using GoogleARCore;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class AnimalController : MonoBehaviour {

    public enum Animals {
        Elephant,
        Gorilla,
        Tiger,
        Sloth,
        Tucan
    };

    public Animals animals;
    private GameObject image = null;
    
    // Start is called before the first frame update
    void Start() {
        string imageName = "";
        switch (animals) {
            case Animals.Elephant:
                imageName = "ElephantImage";
                break;
            case Animals.Gorilla:
                imageName = "GorillaImage";
                break;
            case Animals.Tiger:
                imageName = "TigerImage";
                break;
            case Animals.Sloth:
                imageName = "SlothImage";
                break;
            case Animals.Tucan:
                imageName = "TucanImage";
                break;
        }

        image = GameObject.Find(imageName);
        
    }

    // Update is called once per frame
    void Update() {

    }

    public void OnTouchDetected() {
        transform.Rotate(30, 30, 30);
        
        if(image != null) {
            image.GetComponent<Image>().color = Color.white;
        }
    }
}
