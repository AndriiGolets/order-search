package site.golets.viber.controller;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

@RestController
public class ViberBotCallbackController {

    private final ViberBot bot;
    private final ViberSignatureValidator signatureValidator;

    public ViberBotCallbackController(ViberBot bot, ViberSignatureValidator signatureValidator) {
        this.bot = bot;
        this.signatureValidator = signatureValidator;
    }


    @PostMapping(value = "/", produces = "application/json")
    public String incoming(@RequestBody String json, @RequestHeader("X-Viber-Content-Signature") String serverSideSignature)
            throws ExecutionException, InterruptedException, IOException {

        Preconditions.checkState(signatureValidator.isSignatureValid(serverSideSignature, json), "invalid signature");

        @Nullable InputStream response = bot.incoming(Request.fromJsonString(json)).get();

        return response != null ? CharStreams.toString(new InputStreamReader(response, Charsets.UTF_16)) : null;
    }

}
