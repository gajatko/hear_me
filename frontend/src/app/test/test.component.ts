import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  constructor(private http: HttpClient) { }
  private question: TestQuestion;
  @ViewChild('audioOption') audioPlayerRef: ElementRef;

  public configPromise$: Promise<Array<ConfigMapEntry>> | null = null;
  public testName: String = 'intervals';
  private resolveConfig: Function|null = null;

  private static playWave(byteArray: ArrayBuffer) {
    const blob = new Blob([byteArray], { type: 'audio/wav' });
    const url = URL.createObjectURL(blob);
    const audio = document.getElementById('audioElement') as HTMLAudioElement;
    const source = document.getElementById('sourceElement') as HTMLSourceElement;
    source.src = url;
    audio.load();
    audio.play().then();
  }

  ngOnInit() {
    console.log('init');
    this.configPromise$ = new Promise<Array<ConfigMapEntry>>(resolve => this.resolveConfig = resolve);
    this.loadConfig();
  }

  private loadConfig() {
    console.log('Loading default config...');
    this.http.get('http://localhost:8080/ear/get_config/' + this.testName)
      .subscribe((value: any) => {
        console.log('Gotcha config: ');
        console.log(value);
        this.resolveConfig(value);
      });
  }
  public readInputs() {
    this.forInputs((element, entry) => {
      if (entry.confType === 'boolean') {
        entry.confValue = element.checked;
      } else {
        entry.confValue = element.value;
      }
    });
  }
  public setInputs() {
    this.forInputs((element, entry) => {
      if (entry.confType === 'boolean') {
        element.checked = entry.confValue;
      } else {
        element.value = entry.confValue;
      }
    });
  }


  public forInputs(op: (inputElement: HTMLInputElement, configEntry: ConfigMapEntry) => void) {
    this.configPromise$.then(config => {
      for (let i = 0; i < config.length; i++) {
        const entry = config[i];
        const name = 'conf-' + i;
        const element = <HTMLInputElement>document.getElementsByName(name)[0];
        op(element, entry);
        if (entry.confType === 'boolean') {
          element.checked = entry.confValue;
        } else {
          element.value = entry.confValue;
        }
      }
    });
  }

  public loadQuestion() {
    this.readInputs();
    this.configPromise$.then(config =>
      this.http.post('http://localhost:8080/ear/test/' + this.testName, config)
        .subscribe(value => {
          this.question = <TestQuestion>value;
          this.loadAudio();
        })
    );
  }

  public loadAudio() {
    if (this.question == null) {
      console.log('Question not loaded!');
    } else {
      this.http.post('http://localhost:8080/ear/audio',
        this.question.audioPattern, { responseType: 'arraybuffer' })
        .subscribe(wavBytes => TestComponent.playWave(wavBytes));
    }
  }

  public postAnswer(answer: String) {
    if (this.question == null) {
      console.log('Question not loaded!');
    } else if (this.question.possibleAnswers.findIndex(value => value === answer) === -1) {
      console.log('Answer out of range!');
    } else {
      this.http.get('http://localhost:8080/ear/answer/' + answer)
        .subscribe(value => console.log(value));
    }
  }

  restartTest() {
    this.http.get('http://localhost:8080/ear/restart')
      .subscribe();
  }
}
