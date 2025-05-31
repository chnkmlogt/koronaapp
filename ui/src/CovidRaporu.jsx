
import React, { useEffect, useState } from 'react';
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid,
  Tooltip, Legend, ResponsiveContainer
} from 'recharts';
import { Paginator } from 'primereact/paginator';
import axios from 'axios';

function CovidRaporu() {
  const [grafikVerisi, setGrafikVerisi] = useState([]);
  const [sehir, setSehir] = useState('');
  const [kumulatifMi, setKumulatifMi] = useState(false);
  const [haberMetni, setHaberMetni] = useState('');
  const [haberler, setHaberler] = useState([]);
  const [first, setFirst] = useState(0);
  const [rows, setRows] = useState(5);
  const [totalRecords, setTotalRecords] = useState(0);

  const grafikVerisiniGetir = async () => {
    try {
      const params = {};
      if (sehir) params.city = sehir;
      if (kumulatifMi) params.cumulative = true;
      const res = await axios.get('/api/reports', { params });
      setGrafikVerisi(res.data);
    } catch (err) {
      console.error('Grafik verisi alınamadı:', err);
    }
  };

  const haberListesiniGetir = async () => {
    try {
      const res = await axios.get('/api/news', {
        params: {
          page: first / rows,
          size: rows
        }
      });
      setHaberler(res.data.content);
      setTotalRecords(res.data.totalElements);
    } catch (err) {
      console.error('Haber listesi alınamadı:', err);
    }
  };

  const haberiKaydet = async () => {
    if (!haberMetni.trim()) return;
    try {
      await axios.post('/api/news', haberMetni, {
        headers: { 'Content-Type': 'text/plain' }
      });
      setHaberMetni('');
      grafikVerisiniGetir();
      haberListesiniGetir();
    } catch (err) {
      console.error('Haber gönderilemedi:', err);
    }
  };

  useEffect(() => {
    grafikVerisiniGetir();
    haberListesiniGetir();
  }, [sehir, kumulatifMi, first, rows]);

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">COVID-19 Raporu</h2>

      <div className="flex flex-col gap-4 mb-6">
        <div className="flex gap-4">
          <input
            type="text"
            placeholder="Şehir adı girin (örn. Ankara)"
            className="border px-3 py-2 rounded"
            value={sehir}
            onChange={(e) => setSehir(e.target.value)}
          />
          <label className="flex items-center gap-2">
            <input
              type="checkbox"
              checked={kumulatifMi}
              onChange={(e) => setKumulatifMi(e.target.checked)}
            />
            Kümülatif
          </label>
          <button
            onClick={grafikVerisiniGetir}
            className="bg-blue-600 text-white px-4 py-2 rounded"
          >
            Yenile
          </button>
        </div>

        <div>
          <textarea
            rows={3}
            className="w-full border px-3 py-2 rounded"
            placeholder="Yeni haber metni girin..."
            value={haberMetni}
            onChange={(e) => setHaberMetni(e.target.value)}
          ></textarea>
          <button
            onClick={haberiKaydet}
            className="mt-2 bg-green-700 text-white px-4 py-2 rounded"
          >
            Haberi Gönder
          </button>
        </div>
      </div>

      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={grafikVerisi} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="date" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="totalCases" stroke="#8884d8" name="Vaka" />
          <Line type="monotone" dataKey="totalDeaths" stroke="#ff0000" name="Vefat" />
          <Line type="monotone" dataKey="totalRecovered" stroke="#00C49F" name="İyileşen" />
        </LineChart>
      </ResponsiveContainer>

      <div className="mt-6">
        <h3 className="text-lg font-semibold mb-2">Girilen Haberler</h3>
        <ul className="space-y-2">
          {haberler.map((item, i) => (
            <li key={i} className="border rounded p-2 text-sm">
              <strong>{item.date}</strong> - {item.city} - {item.cases} vaka, {item.deaths} vefat, {item.recovered} iyileşen<br />
              <em className="text-gray-500">{item.rawText}</em>
            </li>
          ))}
        </ul>

        <Paginator
          first={first}
          rows={rows}
          totalRecords={totalRecords}
          onPageChange={(e) => {
            setFirst(e.first);
            setRows(e.rows);
          }}
          template="PrevPageLink PageLinks NextPageLink"
          className="mt-4"
        />
      </div>
    </div>
  );
}

export default CovidRaporu;
