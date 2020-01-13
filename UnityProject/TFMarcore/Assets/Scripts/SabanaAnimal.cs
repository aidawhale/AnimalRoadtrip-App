using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using static SabanaProp;

public class SabanaAnimal : MonoBehaviour {

    public enum Animal {
        Elephant,
        Giraffe,
        Gazelle,
        Lion
    };

    public Animal animalType;
    public Prop desiredGift;
    public GameObject Head;
    public GameObject CheetahPlace;
    public GameObject MainPlayer;
    public Material PinkLionMaterial;
    public Material NormalMaterial;

    private Vector3 position;
    private bool isHappy = false;

    // Start is called before the first frame update
    void Start() {
        position = transform.position;
    }

    public void OnTouchDetected() {
        if (isHappy) {
            Handheld.Vibrate();
            return;
        }
        MainPlayer.SendMessage("OnAnimalClick", this);
    }

    public void OnReceivePresent(SabanaProp prop) {
        isHappy = true;

        if(animalType == Animal.Lion && PinkLionMaterial != null) {
            // Change material
            gameObject.GetComponent<Renderer>().material = PinkLionMaterial;
            return;
        }

        prop.transform.position = Head.transform.position;
        if (prop.GetComponent<BoxCollider>() != null) {
            prop.GetComponent<BoxCollider>().enabled = false;
        }
    }

    public void OnResetPosition() {
        transform.position = position;
        isHappy = false;
        if (animalType == Animal.Lion && NormalMaterial != null) {
            // Change material
            gameObject.GetComponent<Renderer>().material = NormalMaterial;
        }
    }

    public void OnUpdatePosition() {
        position = transform.position;
    }

    public bool IsHappy() {
        return isHappy;
    }

}
