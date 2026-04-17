import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

// Interface que todos os observadores devem implementar
interface Observer {
    void update(Subject s);
}

// Classe base do sujeito 
class Subject {
    private String local; 
    private List<Observer> observers = new ArrayList<Observer>();
    
    public Subject(String local) {
        this.local = local;
    }
    
    public String getLocal() {
        return local;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers() {
        Iterator<Observer> it = observers.iterator();
        while (it.hasNext()) {
            Observer obs = it.next();
            obs.update(this); 
        }
    }
}

class Temperatura extends Subject  {
    private double temp;
    public Temperatura(String local) { super(local); }
    public double getTemp() { return temp; }
    public void setTemp(double temp) {
        this.temp = temp;
        notifyObservers(); 
    }
}

class Ph extends Subject { 
    private double ph; 
    public Ph(String local) { super(local); }
    public double getPh(){ return ph; }
    public void setPh(double ph){
        this.ph = ph;
        notifyObservers(); 
    }
}

class UmidadeAr extends Subject {
    private double umid_ar; 
    public UmidadeAr(String local) { super(local); }
    public double getUmidadeAr(){ return umid_ar; }
    public void setUmidadeAr(double umid_ar){
        this.umid_ar = umid_ar;
        notifyObservers(); 
    }
}

// Estação de monitoramento. Quando uma estação é instanciada, ela já tem os 3 sensores. 
class EstacaoMonitoramento {
    private String local;
    private Temperatura sensorTemp;
    private Ph sensorPh;
    private UmidadeAr sensorUmidade;

    // O construtor precisa do nome do local e cria os 3 sensores 
    public EstacaoMonitoramento(String local) {
        this.local = local;
        this.sensorTemp = new Temperatura(local);
        this.sensorPh = new Ph(local);
        this.sensorUmidade = new UmidadeAr(local);
    }

    // Métodos para as universidades se inscreverem no sensor desejado
    public Temperatura getSensorTemp() { return sensorTemp; }
    public Ph getSensorPh() { return sensorPh; }
    public UmidadeAr getSensorUmidade() { return sensorUmidade; }

    // Métodos para atualizar dados da estação de monitoramento 
    public void atualizarTemperatura(double temp) {
        this.sensorTemp.setTemp(temp);
    }
    
    public void atualizarPh(double ph) {
        this.sensorPh.setPh(ph);
    }
    
    public void atualizarUmidadeAr(double umidade) {
        this.sensorUmidade.setUmidadeAr(umidade);
    }
}
    
// Classe de universidade 
class Universidade implements Observer  {
    private String nome;

    public Universidade(String nome) {
        this.nome = nome;
    }

    public void update(Subject s) { 
        if (s instanceof Temperatura) {
            Temperatura t = (Temperatura) s;
            System.out.println("Alteração na universidade " + this.nome + ". A Temperatura em " + t.getLocal() + " mudou para: " + t.getTemp() + "°C");
        } 
        else if (s instanceof Ph) {
            Ph p = (Ph) s;
            System.out.println("Alteração na universidade " + this.nome + ". O pH em " + p.getLocal() + " mudou para: " + p.getPh());
        } 
        else if (s instanceof UmidadeAr) {
            UmidadeAr u = (UmidadeAr) s;
            System.out.println("Alteração na universidade " + this.nome + ". A Umidade do ar em " + u.getLocal() + " mudou para: " + u.getUmidadeAr() + "%");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        
        EstacaoMonitoramento manaus = new EstacaoMonitoramento("Manaus");
        EstacaoMonitoramento fronteiraLeste = new EstacaoMonitoramento("Fronteira Leste");

        // Criando as Universidades
        Universidade sjc = new Universidade("São José dos Campos");
        Universidade poa = new Universidade("Porto Alegre");
        Universidade sp = new Universidade("São Paulo");
        Universidade bsb = new Universidade("Brasília");

        // exemplos hipotéticos de qual dado cada universidade quer acessar 
        // SP quer verificar temperatura de Manaus e do Leste
        manaus.getSensorTemp().addObserver(sp);
        fronteiraLeste.getSensorTemp().addObserver(sp);

        // POA quer apenas verificar o  pH da Fronteira Leste
        fronteiraLeste.getSensorPh().addObserver(poa);

        // BSB quer verificar todos os dados de Manaus
        manaus.getSensorTemp().addObserver(bsb);
        manaus.getSensorPh().addObserver(bsb);
        manaus.getSensorUmidade().addObserver(bsb);

        // testes
        System.out.println("Atualizando temperatura e umidade do ar de Manaus");
        manaus.atualizarTemperatura(32.5);
        manaus.atualizarUmidadeAr(85.0);

        System.out.println("\nAtualizando temperatura e ph da estação na fronteira leste");
        fronteiraLeste.atualizarTemperatura(29.0);
        fronteiraLeste.atualizarPh(7.2);
    }
}