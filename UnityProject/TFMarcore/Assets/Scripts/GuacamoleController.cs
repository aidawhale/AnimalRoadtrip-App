using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GuacamoleController : MonoBehaviour
{
    public List<GameObject> HoleList;
    public List<GameObject> AnimalList;

    private bool hosting = true;

    private int maxWaitTime = 3;
    private int minWaitTime = 1;
    private int maxTime = 2;
    private float timer = 0.0f;
    private GameObject currentAnimal = null;
    private GameObject localScore = null;
    private GameObject hostScore = null;

    // Start is called before the first frame update
    void Start()
    {
        localScore = GameObject.Find("LocalScore_Panel");
        hostScore = GameObject.Find("HostScore_Panel");
    }

    // Update is called once per frame
    public void Update()
    {

        if (currentAnimal != null)
        {
            return;
        }

        timer += Time.deltaTime;
        if (timer > maxTime)
        {
            timer = 0.0f;
            SpawnAnimal();
        }
    }

    private void SpawnAnimal()
    {
        // Pick random animal and place it on random hole
        int animalNumber = UnityEngine.Random.Range(0, AnimalList.Count);
        int holeNumber = UnityEngine.Random.Range(0, HoleList.Count);
        float rotation = UnityEngine.Random.Range(0, 360);
        maxTime = UnityEngine.Random.Range(minWaitTime, maxWaitTime + 1);

        currentAnimal = AnimalList[animalNumber];
        currentAnimal.gameObject.transform.position = HoleList[holeNumber].transform.position;
        currentAnimal.gameObject.transform.Rotate(Vector3.up, rotation);
        currentAnimal.SetActive(true);
    }

    public void OnGuacamolePunch()
    {
        currentAnimal.SetActive(false);
        currentAnimal = null;

        if (hosting)
        {
            AddScore(localScore);
        }
        else
        {
            AddScore(hostScore);
        }
    }

    private void AddScore(GameObject score)
    {
        // Add +1 score
        if (score != null)
        {
            Text t = score.GetComponentInChildren<Text>();
            t.text = (int.Parse(t.text) + 1).ToString();
        }

    }

    public void RestartGame()
    {
        Handheld.Vibrate();
        SetScene();
    }

    private void SetScene()
    {
        // Hide all animals
        foreach (GameObject animal in AnimalList)
        {
            animal.SetActive(false);
        }

        currentAnimal = null;

        // Reset score counters
        if (localScore != null)
        {
            Text t = localScore.GetComponentInChildren<Text>();
            t.text = "0";
        }
        if (hostScore != null)
        {
            Text t = hostScore.GetComponentInChildren<Text>();
            t.text = "0";
        }
    }
}
