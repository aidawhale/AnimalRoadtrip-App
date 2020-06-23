using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GuacamoleController : MonoBehaviour
{
    public List<GameObject> HoleList;
    public List<GameObject> AnimalList;

    private int maxWaitTime = 3;
    private int minWaitTime = 1;
    private int maxTime = 2;
    private float timer = 0.0f;
    private GameObject currentAnimal = null;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    public void Update()
    {
        timer += Time.deltaTime;

        if (currentAnimal != null)
        {
            return;
        }

        if (timer > maxTime)
        {
            timer = 0.0f;
            SpawnAnimal();
        }
    }

    private void SpawnAnimal()
    {
        // Pick random animal and place it on random hole
        int animalNumber = UnityEngine.Random.Range(0, AnimalList.Count + 1);
        int holeNumber = UnityEngine.Random.Range(0, HoleList.Count + 1);
        maxTime = UnityEngine.Random.Range(minWaitTime, maxWaitTime + 1);

        currentAnimal = AnimalList[animalNumber];
        currentAnimal.gameObject.transform.position = HoleList[holeNumber].transform.position;
        currentAnimal.SetActive(true);
    }

    public void OnGuacamolePunch()
    {
        currentAnimal.SetActive(false);
        currentAnimal = null;

        // Add +1 score
        // TODO ...
    }

    public void RestartGame()
    {
        Handheld.Vibrate();
        SetScene();
    }

    private void SetScene()
    {
        foreach (GameObject animal in AnimalList)
        {
            animal.SetActive(false);
        }

        currentAnimal = null;

        // Reset score counters
        // TODO ...
    }
}
