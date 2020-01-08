using GoogleARCore;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class AnimalController : MonoBehaviour {

    public enum Animals {
        Elephant,
        Gorilla,
        Jaguar,
        Chameleon,
        Tucan
    };

    public Animals animals;
    private GameObject image = null;
    private ParticleSystem confetti = null;
    
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
            case Animals.Jaguar:
                imageName = "JaguarImage";
                break;
            case Animals.Chameleon:
                imageName = "ChameleonImage";
                break;
            case Animals.Tucan:
                imageName = "TucanImage";
                break;
        }

        image = GameObject.Find(imageName);
        confetti = transform.parent.GetComponentInChildren<ParticleSystem>();
        
    }

    public void OnTouchDetected() {
        if (confetti != null) {
            if(confetti.isPlaying) {
                confetti.Stop();
                confetti.time = 0;
            }
            confetti.Play();
        }
        
        if(image != null) {
            image.GetComponent<Image>().color = Color.white;
        }

        Handheld.Vibrate();
    }
}
