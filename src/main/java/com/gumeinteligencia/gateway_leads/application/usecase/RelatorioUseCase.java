package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.application.usecase.mensagem.MensagemUseCase;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelatorioUseCase {

    private final VendedorUseCase vendedorUseCase;
    private final MensagemUseCase mensagemUseCase;

    @Scheduled(cron = "0 0 16 * * *")
    public void enviarRelatorioDiarioVendedores() {
        List<RelatorioContatoDto> relatorio = vendedorUseCase.getRelatorio();

        String arquivo = gerarArquivo(relatorio);

        mensagemUseCase.enviarRelatorio(arquivo, "Relatorio.xlsx");
    }

    private String gerarArquivo(List<RelatorioContatoDto> contatos) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Contatos");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Nome");
            header.createCell(1).setCellValue("Telefone");
            header.createCell(2).setCellValue("Segmento");
            header.createCell(3).setCellValue("Região");
            header.createCell(4).setCellValue("Data de Criação");
            header.createCell(5).setCellValue("Vendedor");

            int rowNum = 1;
            for (RelatorioContatoDto dto : contatos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dto.getNome());
                row.createCell(1).setCellValue(dto.getTelefone());
                row.createCell(2).setCellValue(dto.getSegmento().getDescricao());
                row.createCell(3).setCellValue(dto.getRegiao().getDescricao());
                row.createCell(4).setCellValue(dto.getDataCriacao().toString());
                row.createCell(5).setCellValue(dto.getNomeVendedor());
            }

            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            byte[] planilha;

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                workbook.write(out);
                planilha = out.toByteArray();
            }

            return Base64.getEncoder().encodeToString(planilha);
        } catch (IOException ex) {
            log.error("Erro ao gerar relatório de vendedores", ex);
        }

        return "";
    }

}
