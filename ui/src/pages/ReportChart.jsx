
import React, {useEffect, useRef, useState} from 'react';
import { Chart } from 'primereact/chart';
import { Dropdown } from 'primereact/dropdown';
import { InputSwitch } from 'primereact/inputswitch';
import { Toast } from 'primereact/toast';

import axios from 'axios';

function ReportChart() {
  const [grafikVerisi, setGrafikVerisi] = useState([]);
  const [sehir, setSehir] = useState(null);
  const [kumulatifMi, setKumulatifMi] = useState(false);
  const [sehirler, setSehirler] = useState([]);
  const [grafikTuru, setGrafikTuru] = useState("line");
  const [chartData, setChartData] = useState(null);
  const toast = useRef(null);

  const grafikTurleri = [
    { label: 'Çizgi Grafiği', value: 'line' },
    { label: 'Bar Grafiği', value: 'bar' }
  ];

  const grafikVerisiniGetir = async () => {
    try {
      const params = {};
      if (sehir) params.city = sehir;
      if (kumulatifMi) params.cumulative = true;
      const res = await axios.get(`${import.meta.env.VITE_API_URL}/reports`, { params });
      setGrafikVerisi(res.data);
    } catch (err) {
      toast.current.show({ severity: 'error', summary: 'Hata', detail: 'Grafik verisi alınamadı' });
    }
  };

  const sehirListesiniGetir = async () => {
    try {
      const res = await axios.get(`${import.meta.env.VITE_API_URL}/cities`);
      setSehirler(res.data.map(city => ({ label: city, value: city })));
    } catch (err) {
      toast.current.show({ severity: 'error', summary: 'Hata', detail: 'Şehir listesi alınamadı:'+ err });

    }
  };

  const chartVerisiniHazirla = () => {
    const labels = grafikVerisi.map(g => g.date);
    const vaka = grafikVerisi.map(g => g.totalCases);
    const vefat = grafikVerisi.map(g => g.totalDeaths);
    const iyilesen = grafikVerisi.map(g => g.totalRecovered);

    setChartData({
      labels: labels,
      datasets: [
        {
          label: 'Vaka',
          data: vaka,
          borderColor: '#42A5F5',
          backgroundColor: '#42A5F5',
          fill: false
        },
        {
          label: 'Vefat',
          data: vefat,
          borderColor: '#FF0000',
          backgroundColor: '#FF0000',
          fill: false
        },
        {
          label: 'İyileşen',
          data: iyilesen,
          borderColor: '#00C49F',
          backgroundColor: '#00C49F',
          fill: false
        }
      ]
    });
  };

  useEffect(() => {
    grafikVerisiniGetir();
    sehirListesiniGetir();
    const interval = setInterval(grafikVerisiniGetir, 10000);
    return () => clearInterval(interval);
  }, [sehir, kumulatifMi]);

  useEffect(() => {
    if (grafikVerisi.length > 0) chartVerisiniHazirla();
  }, [grafikVerisi]);

  return (
    <div className="p-4">
      <Toast ref={toast} />
      <div className="flex flex-wrap gap-4 mb-4 items-center">

        <Dropdown showClear
          value={sehir}
          options={sehirler}
          onChange={(e) => setSehir(e.value)}
          placeholder="Şehir seçin"
          className="w-60"
        />
        <Dropdown
          value={grafikTuru}
          options={grafikTurleri}
          onChange={(e) => setGrafikTuru(e.value)}
          placeholder="Grafik Türü"
          className="w-60"
        />


        <InputSwitch
            inputId="kumulatif"
            checked={kumulatifMi}
            onChange={(e) => setKumulatifMi(e.value)}
        />

        <label htmlFor="kumulatif">Kümülatif</label>
      </div>

      <div className="card">
        {chartData && <Chart
            type={grafikTuru}
            data={chartData}
            options={{ maintainAspectRatio: false }}
            height="90vh"
        />}
      </div>
    </div>
  );
}

export default ReportChart;
