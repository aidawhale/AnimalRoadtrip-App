using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GuacamoleAnimalController : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {

    }

    public void OnTouchDetected()
    {
        Handheld.Vibrate();
        gameObject.SendMessageUpwards("OnGuacamolePunch");
    }
}
