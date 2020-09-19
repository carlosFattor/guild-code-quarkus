package org.guildcode.application.service;

import org.guildcode.application.result.FailureDetail;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import java.util.List;

public abstract class ServiceBase {

    @Inject
    private Jsonb jsonb;

    private Logger LOG = Logger.getLogger(this.getClass());

    public void logError(String errorMessage, Object request) {
        logError(errorMessage, request, null);
    }

    public void logError(String errorMessage, Object request, Throwable cause) {
        try {
            StringBuilder message =
                    new StringBuilder().append(errorMessage);
            if (cause != null) {
                message.append(". Causa: ").append(cause.getMessage());
            }
            message.append(". Request: ").append(jsonb.toJson(request));
            LOG.error(message.toString());
        } catch (Exception e) {
            LOG.error(
                    "Erro ao serializar as informacoes da requisicao no tratamento de excecao. Mensagem: " + e.getMessage());
        }
    }

    public void logError(String errorMessage, List<FailureDetail> failureDetails, Object request) {
        StringBuilder messages = new StringBuilder();
        failureDetails.forEach(detail -> messages.append(detail.getDescription()).append(" "));
        logError(messages.toString(), request);
    }
}
