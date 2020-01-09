using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using static SabanaAnimal;
using static SabanaProp;

public class SabanaMainPlayer : MonoBehaviour {

    public GameObject Head;
    public GameObject Flowers;

    private Vector3 position;
    //private GameObject currentProp = null;
    private SabanaProp currentProp = null;
    private int flowerCount = 0;

    // Start is called before the first frame update
    void Start() {
        position = transform.position;
    }

    public void OnTouchDetected() {
        // ...
    }

    public void OnAnimalClick(SabanaAnimal animal) {
        // Walk to that point
        // ...

        if(currentProp == null) {
            Handheld.Vibrate();
            return;
        }

        if(animal.animalType == Animal.Lion) {
            Flowers.SetActive(false);
            animal.SendMessage("OnReceivePresent", currentProp);
            currentProp = null;
            return;
        }

        if(animal.desiredGift == currentProp.prop) {
            animal.SendMessage("OnReceivePresent", currentProp);
            currentProp = null;
            return;
        }

        // Else: error
        Handheld.Vibrate();
    }

    public void OnFlowerClick() {
        flowerCount++;
        if(flowerCount == 3) {
            Flowers.SetActive(true);
            currentProp = Flowers.GetComponent<SabanaProp>();
        }
    }

    public void OnPropClick(SabanaProp prop) {
        if (currentProp != null) {
            // Player is already carrying something
            Handheld.Vibrate();
            return;
        }
        currentProp = prop;
        currentProp.transform.position = Head.transform.position;

        if (currentProp.prop == Prop.Kite) {
            GameObject.FindGameObjectWithTag("Map").SendMessage("BaseVictory");
        }
    }

    public void OnResetPosition() {
        // Init all parameters
        transform.position = position;
        currentProp = null;
        flowerCount = 0;
        Flowers.SetActive(false);
    }

    public void OnUpdatePosition() {
        position = transform.position;
    }

}
