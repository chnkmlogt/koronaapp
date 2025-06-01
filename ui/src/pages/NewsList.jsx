import React, { useEffect, useState, useRef } from 'react';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Paginator } from 'primereact/paginator';
import { Menubar } from 'primereact/menubar';
import { Toast } from 'primereact/toast';
import { Dialog } from 'primereact/dialog';
import axios from 'axios';

function NewsList() {
    const [haberMetni, setHaberMetni] = useState('');
    const [haberler, setHaberler] = useState([]);
    const [first, setFirst] = useState(0);
    const [rows, setRows] = useState(10);
    const [totalRecords, setTotalRecords] = useState(0);
    const [showDialog, setShowDialog] = useState(false);
    const [showChartDialog, setShowChartDialog] = useState(false);
    const toast = useRef(null);

    const menuItems = [
        { label: 'Haber Girişi', command: () => setShowDialog(true) },
        { label: 'Haber Tablosu', command: () => document.getElementById('haber-tablosu').scrollIntoView({ behavior: 'smooth' }) },
        { label: 'Grafik Ekranı', command: () => setShowChartDialog(true) }
    ];

    const haberiKaydet = async () => {
        if (!haberMetni.trim()) return;
        try {
            await axios.post(`${import.meta.env.VITE_API_URL}/news`, haberMetni, {
                headers: { 'Content-Type': 'text/plain' }
            });
            setHaberMetni('');
            setShowDialog(false);
            toast.current.show({ severity: 'success', summary: 'Başarılı', detail: 'Haber başarıyla kaydedildi.' });
            haberListesiniGetir();
        } catch (err) {
            toast.current.show({ severity: 'error', summary: 'Hata', detail: 'Haber gönderilemedi.' });
        }
    };

    const haberListesiniGetir = async () => {
        try {
            const res = await axios.get(`${import.meta.env.VITE_API_URL}/news`, {
                params: {
                    page: first / rows,
                    size: rows
                }
            });
            const data = res.data;
            const content = Array.isArray(data.content) ? data.content : data;
            setHaberler(content);
            setTotalRecords(data.totalElements || content.length);
        } catch (err) {
            toast.current.show({ severity: 'error', summary: 'Hata', detail: 'Haber listesi alınamadı.' });
        }
    };

    useEffect(() => {
        haberListesiniGetir();
    }, [first, rows]);

    return (
        <div className="p-4">
            <Toast ref={toast} />
            <Menubar model={menuItems} className="mb-4" />

            <Dialog
                header="Yeni Haber Girişi"
                visible={showDialog}
                style={{ width: '50vw' }}
                onHide={() => setShowDialog(false)}
                footer={
                    <div>
                        <Button label="İptal" icon="pi pi-times" onClick={() => setShowDialog(false)} className="p-button-text" />
                        <Button label="Kaydet" icon="pi pi-check" onClick={haberiKaydet} autoFocus />
                    </div>
                }
            >
                <InputTextarea
                    value={haberMetni}
                    onChange={(e) => setHaberMetni(e.target.value)}
                    rows={5}
                    cols={60}
                    placeholder="Yeni haber metni girin..."
                    autoResize
                />
            </Dialog>

            <Dialog
                header="Günlük Vaka, Vefat ve İyileşen Grafiği"
                visible={showChartDialog}
                style={{ width: '80vw', height: '80vh' }}
                onHide={() => setShowChartDialog(false)}
            >
                <iframe src="/grafik" title="Grafik" style={{ width: '100%', height: 'calc(100% - 3rem)', border: 'none' }} scrolling="no" />
            </Dialog>

            <div id="haber-tablosu">
                <DataTable value={haberler} stripedRows responsiveLayout="scroll">
                    <Column field="date" sortable header="Tarih"></Column>
                    <Column field="city" sortable header="Şehir"></Column>
                    <Column field="cases" sortable header="Vaka"></Column>
                    <Column field="deaths" sortable header="Vefat"></Column>
                    <Column field="recovered" header="İyileşen"></Column>
                    <Column field="rawText" header="Haber Metni"></Column>
                </DataTable>



                <Paginator
                    first={first}
                    rows={rows}
                    totalRecords={totalRecords}
                    onPageChange={(e) => {
                        setFirst(e.first);
                        setRows(e.rows);
                    }}
                    className="mt-4"
                />
            </div>
        </div>
    );
}

export default NewsList;
