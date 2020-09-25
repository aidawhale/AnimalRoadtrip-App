using Mirror;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GuacamoleController : MonoBehaviour
{
    public List<GameObject> HoleList;

    private AnimalSpawner animalSpawner = null;

    private void Start()
    {
        if (GameObject.Find("AnimalSpawner") == null) { return; }
        animalSpawner = GameObject.Find("AnimalSpawner").GetComponent<AnimalSpawner>();

        foreach (GameObject hole in HoleList)
        {
            animalSpawner.HoleList.Add(hole);
        }
    }

    public void RestartGame()
    {
        Handheld.Vibrate();
        //SetScene();
    }

    private void SetScene() { }
}
